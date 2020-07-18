package cn.liguohao.api.blog.dao;

import cn.liguohao.api.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: TagDao
 * @Description: 文章标签持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:02:32
 */
public interface TagDao extends JpaRepository<Tag,Long>,JpaSpecificationExecutor<Tag> {

    // 条件分页查询所有标签
    @Query(value = "select * from blog_tag where status <> 0 limit ?1,?2", nativeQuery = true)
    List<Tag> findAllByPagingAndName(Integer currentPage, Integer pageSize);

    // 查询总数
    @Query(value = "select count(*) from blog_tag where status <>0 ", nativeQuery = true)
    Long findCount();

    // 根据ID查询文章
    Tag findTagByTid(Long tid);

    // 根据标签名查询标签
    Tag findTagByName(String name);

    // 查询标签引用的次数
    @Query(value = "select count(*) from blog_article_tag where tid=?1", nativeQuery = true)
    Long findATCountByTid(Long tid);

}
