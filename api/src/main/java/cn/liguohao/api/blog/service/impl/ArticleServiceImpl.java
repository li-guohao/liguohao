package cn.liguohao.api.blog.service.impl;

import cn.liguohao.api.blog.dao.ArticleDao;
import cn.liguohao.api.blog.dao.CommentDao;
import cn.liguohao.api.blog.dao.TagDao;
import cn.liguohao.api.blog.entity.Article;
import cn.liguohao.api.blog.entity.Comment;
import cn.liguohao.api.blog.entity.Tag;
import cn.liguohao.api.blog.service.ArticleService;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ArticleServiceImpl
 * @Description: 文章接口实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:03:33
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public void save(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        if(article.getTop()==null) article.setIsOpen(0); //为空默认不置顶
        if(article.getStatus()==null) article.setStatus(1); //默认保存为草稿

        // 标签逻辑处理
        List<Tag> tagList = article.getTags();
        List<Tag> tags = new ArrayList<>();
        for(Tag tag : tagList){
            // 根据标签名称查询
            Tag tagDataBase = tagDao.findTagByName(tag.getName());
            if(tagDataBase!=null){  //标签物理存在
                tagDataBase.setStatus(1);// 设置状态为正常
                // 标签被引用次数+1
                tagDataBase.setReferenceCount(1+tagDataBase.getReferenceCount());
                tags.add(tagDataBase);
            }else{                  //标签不存在，需要新建
                tag.setCreateTime(new Date());
                tag.setStatus(1);
                tag.setReferenceCount(1); //标签创建时被引用一次
                tags.add(tag);
            }
        }
        article.setTags(tags);
        articleDao.save(article);
    }

    @Override
    public PagingData<Article> findAllByPaging(Integer currentPage, Integer pageSize) {
        PagingData<Article> pagingData = new PagingData<Article>(currentPage,pageSize);
        // 查询
        List<Article> articleList = articleDao.findAllByPaging((currentPage-1)*pageSize , pageSize);
        // 设置数据
        pagingData.setDataArray(articleList);
        // 设置总数
        Long count = articleDao.findCountExist();
        pagingData.setTotal(count.intValue());
        // 查询每篇文章的评论数
        for(Article article: articleList){
            List<Comment> commentList = commentDao.findAllByStatusAndTypeAndRelevanceID(1, 1, article.getAid());
            Integer size = commentList.size();
            article.setCommentCount(size.longValue());
        }
        return pagingData;
    }



    @Override
    public Article findArticleByAid(Long aid) {
        Article article = articleDao.findArticleByAidExist(aid);
        // 增加文章浏览量
        Long count = 0L;
        if(article.getReadCount()!=null) count = article.getReadCount();
        count++;
        article.setReadCount(count);
        // 保存更改
        return articleDao.save(article);
    }

    @Override
    @Transactional
    public void deleteArticleByAid(Long aid) {
        // 根据ID查询
        Article article = findArticleByAid(aid);
        article.setStatus(0); //设置状态为已删除
        // 文章所引用的标签 被引用数-1
        List<Tag> tags = article.getTags();
        for(Tag tag: tags){
            tag.setReferenceCount(tag.getReferenceCount()-1);
        }
        // 保存更改
        articleDao.save(article);
    }

    @Override
    public List<Article> findArticlesOrderByUpdateTime(Integer number) {
        return articleDao.findArticlesOrderByUpdateTime(number);
    }

    @Override
    public List<Article> findHotArticles(Integer number) {
        return articleDao.findHotArticles(number);
    }

    @Override
    @Transactional
    public void mergeArticle(Article article) {
        // 获取更新后的标签集合
        List<Tag> tagsUpdated = article.getTags();
        // 根据ID查询
        Article articleRes = findArticleByAid(article.getAid());
        //复制存在的字段到 数据库对象中，覆盖对应的属性
        BeanUtil.copyFieldByIsExist(article,articleRes);
        // 根据集合的变化动态决定标签的引用是+1还是-1
        //更新后的集合 不包含 此标签，代表此标签已经被此文章移除引用 被引用数需要-1
        for(Tag tag : articleRes.getTags()){
            if(!tagsUpdated.contains(tag)){
                tag.setReferenceCount(tag.getReferenceCount()-1);
            }
        }
        // 更新前的集合 不包含 此标签， 代表此标签被此文章添加引用 被引用数需要+1
        for(Tag tag: tagsUpdated){
            if(!articleRes.getTags().contains(tag)){
                tag.setReferenceCount(tag.getReferenceCount()+1);
            }
        }
        //更新时间
        articleRes.setUpdateTime(new Date());
        // 保存更改
        articleDao.save(articleRes);
    }


    @Override
    public PagingData<Article> findArticlesContainTidOrderByUpdateTimeDesc(Integer currentPage, Integer pageSize, Long tid) {
        PagingData<Article> pagingData = new PagingData<Article>(currentPage,pageSize);
        // 查询
        List<Article> articleList = articleDao.findArticlesPagingWhereContainTidOrderByUpdateTimeDesc((currentPage-1)*pageSize , pageSize,tid);
        // 设置数据
        pagingData.setDataArray(articleList);
        // 设置总数
        Long count = articleDao.findArticlesCountWhereContainTidOrderByUpdateTimeDesc(tid);
        pagingData.setTotal(count.intValue());
        // 查询每篇文章的评论数
        for(Article article: articleList){
            List<Comment> commentList = commentDao.findAllByStatusAndTypeAndRelevanceID(1, 1, article.getAid());
            Integer size = commentList.size();
            article.setCommentCount(size.longValue());
        }
        return pagingData;
    }

    @Override
    public PagingData<Article> findArticlePagingWhereTitleLike(Integer currentPage, Integer pageSize, String titlePart) {
        PagingData<Article> pagingData = new PagingData<Article>(currentPage,pageSize);
        titlePart = "%"+titlePart+"%"; //dao层不好处理，这里直接处理
        // 查询
        List<Article> articleList = articleDao.findArticlesPagingWhereTitleLike((currentPage-1)*pageSize , pageSize,titlePart);
        // 设置数据
        pagingData.setDataArray(articleList);
        // 设置总数
        Long count = articleDao.findArticlesCountWhereTitleLike(titlePart);
        pagingData.setTotal(count.intValue());
        // 查询每篇文章的评论数
        for(Article article: articleList){
            List<Comment> commentList = commentDao.findAllByStatusAndTypeAndRelevanceID(1, 1, article.getAid());
            Integer size = commentList.size();
            article.setCommentCount(size.longValue());
        }
        return pagingData;
    }

	@Override
	public PagingData<Article> findArticlePagingWhereTitleLikeAndIsOpen(Integer currentPage, Integer pageSize,
			String titlePart, Integer isOpen) {
		PagingData<Article> pagingData = new PagingData<Article>(currentPage,pageSize);
        if(titlePart==null || "null".equals(titlePart)) {
        	titlePart = "%%";
        }else {
        	titlePart = "%"+titlePart+"%"; //dao层不好处理，这里直接处理
        }
        // 查询
        List<Article> articleList = articleDao.findArticlesPagingWhereTitleLikeAndIsOpen((currentPage-1)*pageSize , pageSize,titlePart,isOpen);
        // 设置数据
        pagingData.setDataArray(articleList);
        // 设置总数
        Long count = articleDao.findArticlesCountWhereTitleLike(titlePart);
        pagingData.setTotal(count.intValue());
        // 查询每篇文章的评论数
        for(Article article: articleList){
            List<Comment> commentList = commentDao.findAllByStatusAndTypeAndRelevanceID(1, 1, article.getAid());
            Integer size = commentList.size();
            article.setCommentCount(size.longValue());
        }
        return pagingData;
	}

	@Override
	public Article topArticleByAid(Long aid) {
		// 查询是否存在已经置顶文章，返回已经置顶的文章
		List<Article> articleList = articleDao.findArticlesByTop(1);
		if(articleList != null && articleList.size()>0) { //存在已置顶文章
			Article article = articleList.get(0);
			if(article.getAid() == aid){ //操作的正是已经置顶的文章
				//取消文章置顶
				article.setTop(0);
				articleDao.save(article);
			}
			return article;
		}else { // 不存在已置顶文章，置顶文章 
			Article article = articleDao.getOne(aid);
			if(article.getTop()==0) {
				article.setTop(1);
			}else {
				article.setTop(0);
			}
			articleDao.save(article);
			return null;
		}
	}
}
