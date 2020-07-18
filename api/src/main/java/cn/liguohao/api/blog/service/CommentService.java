package cn.liguohao.api.blog.service;

import cn.liguohao.api.blog.entity.Comment;
import cn.liguohao.api.response.PagingData;

/**
 * @ClassName: CommentService
 * @Description: 评论服务层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:04:18
 */
public interface CommentService {

    // 分页查询
    PagingData<Comment> findAllByPaging(Integer currentPage, Integer pageSize);

    // 添加评论
    void save(Comment comment);

    // 根据CID查询评论信息
    Comment findCommentByCid(Long cid);

    // 根据CID删除评论
    void deleteCommentByCid(Long cid);

    // 更新评论
    Comment updataComment(Comment originalComment);

}
