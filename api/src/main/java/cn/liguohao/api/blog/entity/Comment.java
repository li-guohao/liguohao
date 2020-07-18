package cn.liguohao.api.blog.entity;


import cn.liguohao.api.system.entity.User;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: Comment
 * @Description: 评论实体类
 * @author: li-guohao
 * @date: 2020-7-18 1:02:59
 */
@Entity
@Table(name = "blog_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Long cid;    // 评论ID

    @Column(name = "type")
    private Integer type; //类型 1-文章评论 2-... 其它评论

    @Column(name = "rele_id")
    private Long relevanceID; //关联的ID 如文章的评论关联文章的AID

    @Column(name = "content")
    private String content; //评论内容

    @Column(name = "create_time")
    private Date createTime;      //评论时间

    @Column(name = "status")
    private Integer status; //评论状态 0-已删除 1-正常

    @ManyToOne(cascade = CascadeType.REFRESH) //级联查询
    @JoinColumn(name = "user_uid")
    private User user;   //关联的用户

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRelevanceID() {
        return relevanceID;
    }

    public void setRelevanceID(Long relevanceID) {
        this.relevanceID = relevanceID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
