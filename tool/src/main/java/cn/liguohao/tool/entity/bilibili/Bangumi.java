package cn.liguohao.tool.entity.bilibili;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: Bangumi
* @Description: 番剧实体类
* @author liguohao
* @date 2020年7月28日
 */
@Entity
@Table(name = "bili_bangumi")
public class Bangumi {
	
	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "biliVid") 未写此注解则默认字段和属性名称相同
    private Long bilibid;
    
    
    /**
	 * 结果类型
	 *  media_bangumi：番剧
	 *  media_ft：影视  
	 */
    @Column(name = "type")
	private String type;
    
    /**
	 * 剧集mdID
	 */
    @Column(name = "media_id")
	private Long media_id;
	
	/**
	 * 剧集ssID
	 */
    @Column(name = "season_id")
	private Long season_id;
    
	/**
	 * 剧集标题
	 *  关键字用xml标签<em class="keyword">标注
	 */
    @Column(name = "title")
	private String title;
	
	/**
	 * 剧集封面url
	 */
    @Column(name = "cover")
	private String cover;
	
	/**
	 * 剧集类型
	 *  1：番剧  2：电影    3：纪录片
	 *  4：国创  5：电视剧 7：综艺
	 */
    @Column(name = "media_type")
	private Integer media_type;
	
	/**
	 * 地区
	 */
    @Column(name = "areas")
	private String areas;
	
	/**
	 * 风格
	 */
    @Column(name = "styles")
	private String styles;
	
	/**
	 * 声优
	 */
    @Column(name = "cv",columnDefinition="mediumtext")
	private String cv;
	
	/**
	 * 制作组
	 */
    @Column(name = "staff",columnDefinition="mediumtext")
	private String staff;
	
	/**
	 * 剧集重定向url
	 */
    @Column(name = "goto_url")
	private String goto_url;
	
	/**
	 * 简介
	 */
    @Column(name = "description",columnDefinition="mediumtext")
	private String desc;
	
	
	/**
	 * 开播时间
	 */
    @Column(name = "pubtime")
	private Date pubtime;
    
    /**
     * 关键词
     */
    @Column(name = "keyword")
    private String keyword;

    

	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public Long getBilibid() {
		return bilibid;
	}


	public void setBilibid(Long bilibid) {
		this.bilibid = bilibid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Long getMedia_id() {
		return media_id;
	}


	public void setMedia_id(Long media_id) {
		this.media_id = media_id;
	}


	public Long getSeason_id() {
		return season_id;
	}


	public void setSeason_id(Long season_id) {
		this.season_id = season_id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCover() {
		return cover;
	}


	public void setCover(String cover) {
		this.cover = cover;
	}


	public Integer getMedia_type() {
		return media_type;
	}


	public void setMedia_type(Integer media_type) {
		this.media_type = media_type;
	}


	public String getAreas() {
		return areas;
	}


	public void setAreas(String areas) {
		this.areas = areas;
	}


	public String getStyles() {
		return styles;
	}


	public void setStyles(String styles) {
		this.styles = styles;
	}


	public String getCv() {
		return cv;
	}


	public void setCv(String cv) {
		this.cv = cv;
	}


	public String getStaff() {
		return staff;
	}


	public void setStaff(String staff) {
		this.staff = staff;
	}


	public String getGoto_url() {
		return goto_url;
	}


	public void setGoto_url(String goto_url) {
		this.goto_url = goto_url;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Date getPubtime() {
		return pubtime;
	}


	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}
	

}
