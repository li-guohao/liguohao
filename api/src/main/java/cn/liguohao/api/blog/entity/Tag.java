package cn.liguohao.api.blog.entity;

import javax.persistence.*;
import java.util.*;

/**
 * @ClassName: Tag
 * @Description: 标签实体类
 * @author: li-guohao
 * @date: 2020-7-18 1:03:18
 */
@Entity
@Table(name = "blog_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private Long tid;   //标签ID

    @Column(name = "name")
    private String name; //标签名称

    @Column(name = "reference_count")
    private Integer referenceCount ; //标签被引用的次数

    @Column(name = "create_time")
    private Date createTime;    //创建时间

    @Column(name = "status")
    private Integer status;     //状态 0-已删除 1-正常





    public Tag() {
    }

    public Tag(String name, Date createTime) {
        this.name = name;
        this.createTime = createTime;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }
}

