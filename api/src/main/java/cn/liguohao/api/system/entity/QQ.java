package cn.liguohao.api.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: QQ用户信息，绑定用户表的邮箱字段(邮箱唯一)
 * @Description: TODO
 * @author: li-guohao
 * @date: 2020-7-21 22:19:32
 */
@Entity
@Table(name = "system_qq")
public class QQ {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qid")
    private Long qid;                //主键ID，自增
	
	
	@Column(name = "user_email")
	private String userEmail;  		// 绑定的系统用户邮箱
	
	
	@Column(name = "token")
	private String token;  		// 请求令牌

	@Column(name = "open_id")
	private String openID;		// 唯一标识符
	
	@Column(name = "nickname")
	private String nickname;	// 昵称
	
	@Column(name = "figureurl")
	private String figureurl;	// 大小为30×30像素的QQ空间头像URL。
	
	@Column(name = "figureurl_1")
	private String figureurl_1;	// 大小为50×50像素的QQ空间头像URL。
	
	@Column(name = "figureurl_2")
	private String figureurl_2;	// 大小为100×100像素的QQ空间头像URL。
	
	
	@Column(name = "gender")
	private String gender;	// 性别。 如果获取不到则默认返回"男"


	public Long getQid() {
		return qid;
	}


	public void setQid(Long qid) {
		this.qid = qid;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getOpenID() {
		return openID;
	}


	public void setOpenID(String openID) {
		this.openID = openID;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getFigureurl() {
		return figureurl;
	}


	public void setFigureurl(String figureurl) {
		this.figureurl = figureurl;
	}


	public String getFigureurl_1() {
		return figureurl_1;
	}


	public void setFigureurl_1(String figureurl_1) {
		this.figureurl_1 = figureurl_1;
	}


	public String getFigureurl_2() {
		return figureurl_2;
	}


	public void setFigureurl_2(String figureurl_2) {
		this.figureurl_2 = figureurl_2;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	

}
