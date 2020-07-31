package cn.liguohao.tool.entity.bilibili;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @ClassName: Video
* @Description: 哔哩哔哩视频信息类
* @author liguohao
* @date 2020年7月27日
*
 */
@Entity
@Table(name = "bili_video")
public class Video {

	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "biliVid") 未写此注解则默认字段和属性名称相同
    private Long biliVid;
    
    /**
	 * 视频bvID
	 */
    @Column(name = "bvid")
	private String bvid;
	
	/**
	 * 视频avID
	 */
    @Column(name = "aid")
	private Long aid;
	
	/**
	 * 视频分P总数 默认为1
	 */
    @Column(name = "videos")
	private Integer videos;
	
	/**
	 * 分区ID
	 */
    @Column(name = "tid")
	private Integer tid;
	
	/**
	 * 子分区名称
	 */
    @Column(name = "tname")
	private String tname;

	/**
	 * 版权标志 1：自制 2：转载
	 */
    @Column(name = "copyright")
	private Integer copyright;
	
	/**
	 * 视频封面图片url
	 */
    @Column(name = "pic")
	private String pic;
	
	/**
	 * 视频标题
	 */
    @Column(name = "title")
	private String title;
	
	/**
	 * 视频上传时间 时间戳
	 */
    @Column(name = "pubdate")
	private Long pubdate;
	
	/**
	 * 视频审核通过时间 时间戳
	 */
    @Column(name = "ctime")
	private Long ctime;
	
	/**
	 * 视频简介 
	 */
    @Column(name = "desciption",columnDefinition="mediumtext")
	private String desc;
	
	/**
	 * 视频总计持续时长（所有分P） 单位为秒
	 */
    @Column(name = "duration")
	private Long duration;
	
	
	/**
	 * 重定向url 用于番剧&影视的av->ep
	 */
    @Column(name = "redirect_url")
	private String redirect_url;
    
	/**
	 * 查询的次数
	 */
    @Column(name ="searchCount")
    private Integer searchCount;

	public Long getBiliVid() {
		return biliVid;
	}

	public void setBiliVid(Long biliVid) {
		this.biliVid = biliVid;
	}

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

	
	public Long getPubdate() {
		return pubdate;
	}

	public void setPubdate(Long pubdate) {
		this.pubdate = pubdate;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
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

	public Integer getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	} 
    
    
    
}
