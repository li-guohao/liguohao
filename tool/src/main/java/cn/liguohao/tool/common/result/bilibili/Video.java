package cn.liguohao.tool.common.result.bilibili;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @ClassName: Video
* @Description: 哔哩哔哩返回视频对象
* @author liguohao
* @date 2020年7月27日
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {
	
	/**
	 * 视频bvID
	 */
	private String bvid;
	
	/**
	 * 视频avID
	 */
	private Long aid;
	
	/**
	 * 视频分P总数 默认为1
	 */
	private Integer videos;
	
	/**
	 * 分区ID
	 */
	private Integer tid;
	
	/**
	 * 子分区名称
	 */
	private String tname;

	/**
	 * 版权标志 1：自制 2：转载
	 */
	private Integer copyright;
	
	/**
	 * 视频封面图片url
	 */
	private String pic;
	
	/**
	 * 视频标题
	 */
	private String title;
	
	/**
	 * 视频上传时间 时间戳
	 */
	private Date pubdate;
	
	/**
	 * 视频审核通过时间 时间戳
	 */
	private Date ctime;
	
	/**
	 * 视频简介 
	 */
	private String desc;
	
	/**
	 * 视频总计持续时长（所有分P） 单位为秒
	 */
	private Long duration;
	
	
	/**
	 * 重定向url 用于番剧&影视的av->ep
	 */
	private String redirect_url;
	
	/**
	 * 视频属性标志
	 */
	private VideoRight rights;
	
	/**
	 * 视频UP主信息
	 */
	private VideoOwner owner;

	public String getBvid() {
		return bvid;
	}

	public void setBvid(String bvid) {
		this.bvid = bvid;
	}

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public Integer getVideos() {
		return videos;
	}

	public void setVideos(Integer videos) {
		this.videos = videos;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getCopyright() {
		return copyright;
	}

	public void setCopyright(Integer copyright) {
		this.copyright = copyright;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}


	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public VideoRight getRights() {
		return rights;
	}

	public void setRights(VideoRight rights) {
		this.rights = rights;
	}

	public VideoOwner getOwner() {
		return owner;
	}

	public void setOwner(VideoOwner owner) {
		this.owner = owner;
	}
	
	
}
