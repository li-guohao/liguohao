package cn.liguohao.api.blog.service.impl;

import cn.liguohao.api.blog.dao.CommentDao;
import cn.liguohao.api.blog.entity.Comment;
import cn.liguohao.api.blog.service.CommentService;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: CommentServiceImpl
 * @Description: 评论服务层接口实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:03:42
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;


    @Override
    public void save(Comment comment) {
        comment.setCreateTime(new Date());
        if(comment.getStatus()==null || "".equals(comment.getStatus().toString())) comment.setStatus(1);   //状态默认可用
        commentDao.save(comment);
    }

    @Override
    public PagingData<Comment> findAllByPaging(Integer currentPage, Integer pageSize) {
        // 新建返回结果对象
        PagingData<Comment> pagingData = new PagingData<>();
        // 设置返回值
        pagingData.setCurrentPage(currentPage);
        pagingData.setPageSize(pageSize);
        // 查询总数并设置
        Long count = commentDao.count();
        pagingData.setTotal(count.intValue());
        // 分页查询数据
        List<Comment> commentList = commentDao.findAllByPaging((currentPage-1)*pageSize, pageSize);
        pagingData.setDataArray(commentList);
        return pagingData;
    }


    @Override
    public Comment findCommentByCid(Long cid) {
        return commentDao.findCommentBycid(cid);
    }

    @Override
    public void deleteCommentByCid(Long cid) {
        // 根据cid查询
        Comment comment = findCommentByCid(cid);
        if(comment==null) return;
        // 更新状态
        comment.setStatus(0);
        // 保存更新
        commentDao.save(comment);
    }

    @Override
    public Comment updataComment(Comment originalComment) {
        // 根据cid查询
        Comment comment = findCommentByCid(originalComment.getCid());
        if(comment==null) return null;
        // 更新状态
        BeanUtil.copyFieldByIsExist(originalComment,comment);
        // 保存更新
        commentDao.save(comment);
        return comment;
    }
}
