package cn.liguohao.api.blog.entity;

import javax.persistence.*;
import java.util.*;

/**
 * @ClassName: Article
 * @Description: 文章实体类
 * @author: li-guohao
 * @date: 2020-7-18 1:02:50
 */
@Entity
@Table(name = "blog_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Long aid;       //主键ID，自增

    @Column(name = "title")
    private String title;   //标题

    @Column(name = "thumbnail")
    private String thumbnail; //首页图Url

    @Column(name = "description")
    private String description;       //文章简述

    @Column(name = "create_time")
    private Date createTime;    //创建时间

    @Column(name = "update_time")
    private Date updateTime;       //更新时间

    @Column(name = "content",columnDefinition="mediumtext")
    private String content;         //内容

    @Column(name = "comment_count")
    private Long commentCount;      //文章评论数

    @Column(name = "readCount")
    private  Long readCount;      //文章浏览数

    @Column(name = "author")
    private String author;        //作者

    @Column(name = "status")
    private Integer status;             //状态 0-已删除 1-正常
    
    @Column(name = "is_open")
    private Integer isOpen;				// 发布状态 0-草稿(下架) 1-已发布(上架)

    @Column(name = "top")
    private Integer top;		//是否置顶  0-未置顶 1-置顶 有且只有一篇文章置顶

    //配置和标签的多对多关系映射
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="blog_article_tag",//中间表的名称
            //中间表article_tag字段关联article表的主键字段aid
            joinColumns={@JoinColumn(name="aid",referencedColumnName="aid")},
            //中间表article_tag的字段关联tag表的主键tid
            inverseJoinColumns={@JoinColumn(name="tid",referencedColumnName="tid")})
    private List<Tag> tags = new ArrayList<>();//标签ID集合


    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}
	

}
