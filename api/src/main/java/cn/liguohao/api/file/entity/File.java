package cn.liguohao.api.file.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: File
 * @Description: 文件数据实体类
 * @author: li-guohao
 * @date: 2020-7-18 1:05:57
 */
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid")
    private Long fid; // 图片主键ID

    @Column(name = "name")
    private String name; // 文件名称

    @Column(name = "description")
    private String description; //描述

    /**
     * 文件类型为图片 type=img
     * 文件类型为文档 type=doc
     * 文件类型为音频 type=audio
     * 文件类型为视频 type=video
     */
    @Column(name = "type")
    private String type; // 文件类型

    @Column(name = "upload_time")
    private Date uploadTime; // 文件上传时间

    @Column(name = "upper")
    private String upper;// 上传者名称

    @Column(name = "url")
    private String url; // 文件的直链路径

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
