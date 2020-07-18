package cn.liguohao.api.blog.service;

import cn.liguohao.api.blog.entity.Article;
import cn.liguohao.api.response.PagingData;

import java.util.List;

/**
 * @ClassName: ArticleService
 * @Description: 文章服务层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:04:08
 */
public interface ArticleService {

    // 添加文章
    void save(Article article);

    // 分页查询所有文章
    PagingData<Article> findAllByPaging(Integer currentPage, Integer pageSize);

    // 根据ID查询文章
    Article findArticleByAid(Long aid);

    //  更新文章
    void mergeArticle(Article article);

    // 根据ID 删除文章
    void deleteArticleByAid(Long aid);

    // 根据更新时间排序查询最新的文章,传入需要查询的最新数据条数和类型
    List<Article> findArticlesOrderByUpdateTime(Integer number);

    /**
     * 查询浏览数由高到低的5篇文章
     *  type:文章类型 ，一般默认为2长文章
     *  number: 查询的数量
     */
    List<Article> findHotArticles(Integer number);

    /**
     * 根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列, 最新的在前面
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @param tid 包含的标签ID
     * @return 包含数据的分页对象
     */
    PagingData<Article> findArticlesContainTidOrderByUpdateTimeDesc(Integer currentPage, Integer pageSize, Long tid);

    /**
     * @Title: findArticlePagingWhereTitleLike
     * @Description: 后台根据文章标题关键词模糊查询文章
     * @param currentPage 当前页
     * @param pageSize	每页条数
     * @param titlePart 文章标题片段
     * @return
     * @return: PagingData<Article>
     */
    PagingData<Article> findArticlePagingWhereTitleLike(Integer currentPage, Integer pageSize, String titlePart);
    
    /**
     * @Title: findArticlePagingWhereTitleLikeAndIsOpen
     * @Description: 前台根据文章标题关键词模糊查询文章
     * @param currentPage 当前页
     * @param pageSize	每页条数
     * @param titlePart 文章标题片段
     * @param isOpen 是否上架
     * @return
     * @return: PagingData<Article>
     */
	PagingData<Article> findArticlePagingWhereTitleLikeAndIsOpen(Integer currentPage, Integer pageSize,
			String titlePart, Integer isOpen);

}
