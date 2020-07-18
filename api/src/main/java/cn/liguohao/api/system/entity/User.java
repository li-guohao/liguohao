package cn.liguohao.api.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author 李国豪
 * @Date 2020/6/11 8:20
 */
@Entity
@Table(name = "system_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;                //主键ID，自增

    @Column(name = "username")
    private String username;        //用户名

    @Column(name = "password")
    private String password;        //密码

    @Column(name = "nickname")
    private String nickname;        //昵称

    @Column(name = "head_portrait_url")
    private String headPortraitUrl; //头像地址

    @Column(name = "telephone")
    private Long telephone;         //手机号

    @Column(name = "token")
    private String token;           //登陆令牌

    @Column(name = "email")
    private String email;           //邮箱


    @Column(name = "BUID")
    private Long BUID;      //B站UUID

    @Column(name = "qq")
    private Long qq;                //QQ号码

    @Column(name = "wechat")
    private String wechat;          //微信号

    @Column(name = "website")
    private String website;         //个人主页站点

    @Column(name = "description")
    private String description;     //个人简介

    @Column(name = "register_time")
    private Date registerTime;      //注册时间

    @Column(name = "status")
    private Integer status;         //用户状态 0-已删除 1-正常


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getBUID() {
        return BUID;
    }

    public void setBUID(Long BUID) {
        this.BUID = BUID;
    }
}
