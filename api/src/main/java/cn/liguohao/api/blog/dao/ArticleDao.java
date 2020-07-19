package cn.liguohao.api.blog.dao;

import cn.liguohao.api.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: ArticleDao
 * @Description: 文章持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:02:02
 */
public interface ArticleDao extends JpaRepository<Article,Long>,JpaSpecificationExecutor<Article> {

    // 分页查询所有文章
    @Query(value = "select * from blog_article where status <> 0  limit ?1,?2 ", nativeQuery = true)
    List<Article> findAllByPaging(Integer currentPage,Integer pageSize);

    // 查询总数，根据条件
    @Query(value = "select count(*) from blog_article where status <> 0 ", nativeQuery = true)
    Long findCountExist();

    // 根据ID查询文章
    @Query(value = "select * from blog_article where status <> 0 and aid=?1", nativeQuery = true)
    Article findArticleByAidExist(Long aid);

    // 根据ID删除文章
    //Article deleteArticleByAid(Long aid);

    // 前台根据更新时间排序查询最新的文章,传入需要查询的最新数据条数和类型
    @Query(value = "select * from blog_article where status <> 0 and is_open <> 0  order by update_time desc limit 0,?1", nativeQuery = true)
    List<Article> findArticlesOrderByUpdateTime(Integer number);

    /**
              *     前台查询浏览数由高到低的5篇文章
     *  type:文章类型 ，一般默认为2长文章
     *  number: 查询的数量
      */
    @Query(value = "select * from blog_article where status <> 0 and is_open <> 0  order by read_count desc limit 0,?1", nativeQuery = true)
    List<Article> findHotArticles(Integer number);

    // 前台根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列, 最新的在前面, 分页
    @Query(value = "select a.* " +
                    "from blog_article a,blog_article_tag t where  t.aid=a.aid and status <> 0 and is_open <> 0 and t.tid=?3 " +
                    "order by a.update_time desc limit ?1,?2"
                    ,nativeQuery = true)
    List<Article> findArticlesPagingWhereContainTidOrderByUpdateTimeDesc(Integer currentPage, Integer pageSize,Long tid);
    // 前台根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列, 最新的在前面， 获取总数
    @Query(value = "select count(*) " +
                    "from blog_article a,blog_article_tag t where  t.aid=a.aid and status <> 0 and is_open <> 0  and t.tid=?1 " +
                    "order by a.update_time desc "
                    ,nativeQuery = true)
    Long findArticlesCountWhereContainTidOrderByUpdateTimeDesc(Long tid);

    // 前台根据文章标题模糊查询
    @Query(value = "select count(*) from blog_article where status <> 0 and is_open <> 0  and title like ?1",nativeQuery = true)
    Long findArticlesCountWhereTitleLike(String titlePart);
    
    // 后台查询所有文章
    @Query(value = "select * from blog_article where status <> 0  and title like ?3 limit ?1,?2",nativeQuery = true)
    List<Article> findArticlesPagingWhereTitleLike(Integer currentPage, Integer pageSize, String titlePart);
    
    // 前台门户查询所有文章 需要判断是否上架(已发布)
    @Query(value = "select * from blog_article where status <> 0 and is_open=?4 and title like ?3  limit ?1,?2 ",nativeQuery = true)
    List<Article> findArticlesPagingWhereTitleLikeAndIsOpen(Integer currentPage, Integer pageSize, String titlePart, Integer isOpen);

    // 查询置顶的文章
    List<Article> findArticlesByTop(Integer top);


}
