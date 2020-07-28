package cn.liguohao.tool.common.result.bilibili.search.bangumi;

import java.util.Date;
import java.util.List;

/**
* @ClassName: Bangumi
* @Description: 番剧&影视实体类
* @author liguohao
* @date 2020年7月28日
 */
public class Bangumi {

	/**
	 * 结果类型
	 *  media_bangumi：番剧
	 *  media_ft：影视  
	 */
	private String type;
	
	/**
	 * 剧集mdID
	 */
	private Long media_id;
	
	/**
	 * 剧集ssID
	 */
	private Long season_id;
	/**
	 * 剧集标题
	 *  关键字用xml标签<em class="keyword">标注
	 */
	private String title;
	
	/**
	 * 剧集封面url
	 */
	private String cover;
	
	/**
	 * 剧集类型
	 *  1：番剧  2：电影    3：纪录片
	 *  4：国创  5：电视剧 7：综艺
	 */
	private Integer media_type;
	
	/**
	 * 地区
	 */
	private String areas;
	
	/**
	 * 风格
	 */
	private String styles;
	
	/**
	 * 声优
	 */
	private String cv;
	
	/**
	 * 制作组
	 */
	private String staff;
	
	/**
	 * 剧集重定向url
	 */
	private String goto_url;
	
	/**
	 * 简介
	 */
	private String desc;
	
	/**
	 * 角标有无
	 */
	private Integer corner;
	
	/**
	 * 开播时间
	 */
	private Date pubtime;
	
	/**
	 * 开播时间重写信息  
	 * 优先级高于pubtime
	 * 可为空
	 */
	private String fix_pubtime_str;
	
	/**
	 * 评分信息
	 */
	private MediaScore media_score;
	
	/**
	 * 关键字匹配类型	
	 */
	private List<String> hit_columns;
	
	/**
	 * 剧集标志信息
	 */
	private List<DisplayInfo> display_info;
	
	/**
	 * 关键字匹配分集标题的分集epID
	 * 多个用,分隔	
	 */
	private String hit_epids;
	
	/**
	 * 剧集类型:
	 * 	1：番剧  2：电影    3：纪录片
	 *  4：国创 5：电视剧 7：综艺
	 */
	private Integer season_type;
	
	/**
	 * 剧集重定向url
	 */
	private String url;
	
	/**
	 * 是否追番
	 * 需要登录(SESSDATA)
	 * 未登录则恒为0
	 * 0：否     1：是
	 */
	private Integer is_follow;
	
	/**
	 * 结果匹配的分集信息
	 */
	private List<Ep> eps;
	
	/**
	 * 剧集标志信息
	 */
	private List<Badge> badges;

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

	public Integer getCorner() {
		return corner;
	}

	public void setCorner(Integer corner) {
		this.corner = corner;
	}

	public Date getPubtime() {
		return pubtime;
	}

	public void setPubtime(Date pubtime) {
		this.pubtime = pubtime;
	}

	public String getFix_pubtime_str() {
		return fix_pubtime_str;
	}

	public void setFix_pubtime_str(String fix_pubtime_str) {
		this.fix_pubtime_str = fix_pubtime_str;
	}

	public MediaScore getMedia_score() {
		return media_score;
	}

	public void setMedia_score(MediaScore media_score) {
		this.media_score = media_score;
	}

	public List<String> getHit_columns() {
		return hit_columns;
	}

	public void setHit_columns(List<String> hit_columns) {
		this.hit_columns = hit_columns;
	}

	public List<DisplayInfo> getDisplay_info() {
		return display_info;
	}

	public void setDisplay_info(List<DisplayInfo> display_info) {
		this.display_info = display_info;
	}

	public String getHit_epids() {
		return hit_epids;
	}

	public void setHit_epids(String hit_epids) {
		this.hit_epids = hit_epids;
	}

	public Integer getSeason_type() {
		return season_type;
	}

	public void setSeason_type(Integer season_type) {
		this.season_type = season_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIs_follow() {
		return is_follow;
	}

	public void setIs_follow(Integer is_follow) {
		this.is_follow = is_follow;
	}

	public List<Ep> getEps() {
		return eps;
	}

	public void setEps(List<Ep> eps) {
		this.eps = eps;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}
	
	
}

