package cn.liguohao.api.blog.dao;

import cn.liguohao.api.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: CommentDao
 * @Description: 评论持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:02:12
 */
public interface CommentDao extends JpaRepository<Comment,Long>,JpaSpecificationExecutor<Comment> {

    // 条件分页查询所有标签
    @Query(value = "select * from blog_comment where status <> 0 limit ?1,?2", nativeQuery = true)
    List<Comment> findAllByPaging(Integer currentPage, Integer pageSize);

    // 根据ID查询评论
    Comment findCommentBycid(Long cid);

    // 根据评论类型和关联的ID查询总数
    List<Comment> findAllByStatusAndTypeAndRelevanceID(Integer status, Integer type, Long relevanceID);
}


