package cn.liguohao.api.blog.entity;

import javax.persistence.*;

/**
 * @ClassName: Link
 * @Description: 友情链接
 * @author: li-guohao
 * @date: 2020-7-18 1:03:09
 */
@Entity
@Table(name = "blog_link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid")
    private Long lid;

    @Column(name = "name")
    private String name; //链接名称

    @Column(name = "icon_class")
    private String iconClass;  //图标样式

    @Column(name = "icon_url")
    private String iconUrl; //图标地址

    @Column(name = "img")
    private String img;  //图像

    @Column(name = "indroduced")
    private String indroduced; //介绍

    @Column(name = "target_url")
    private String targetUrl; //链接地址

    @Column(name = "is_blank")
    private boolean isBlank; //是否打开新窗口

    @Column(name = "partent_lid")
    private Long partentLid; //父导航链接ID


    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIndroduced() {
        return indroduced;
    }

    public void setIndroduced(String indroduced) {
        this.indroduced = indroduced;
    }


    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public boolean isBlank() {
        return isBlank;
    }

    public void setBlank(boolean blank) {
        isBlank = blank;
    }

    public Long getPartentLid() {
        return partentLid;
    }

    public void setPartentLid(Long partentLid) {
        this.partentLid = partentLid;
    }
}
