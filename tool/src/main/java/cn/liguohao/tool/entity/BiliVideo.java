package cn.liguohao.tool.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

/**
* @ClassName: BiliVideoCover.java
* @Description: 哔哩哔哩视频封面类
* @author: 李国豪
* @date: 2020年5月9日 下午6:13:40
 */
@Entity
@Table(name = "bili_video")
public class BiliVideo {

	//主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") 未写此注解则默认字段和属性名称相同
    private Long id;
    
    @Value("title")
    private String title; //标题
    
    @Value("bvid")
    private String bvid; //视频的BVID
    
    @Value("avid")
    private String avid; //视频的AVID
    
    @Value("cover_img_url")
    private String coverImgUrl; //封面图片地址
    
    @Value("search_date")
    private Date serachDate; //查询时间
    
    @Value("ip_address")
    private String IpAddress; //查询用户IP
    
    @Value("searchCount")
    private Integer searchCount; //查询的次数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getBvid() {
		return bvid;
	}

	public void setBvid(String bvid) {
		this.bvid = bvid;
	}

	public String getAvid() {
		return avid;
	}

	public void setAvid(String avid) {
		this.avid = avid;
	}

	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	public Date getSerachDate() {
		return serachDate;
	}

	public void setSerachDate(Date serachDate) {
		this.serachDate = serachDate;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}

	public Integer getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Integer searchCount) {
		this.searchCount = searchCount;
	}
    
    
}
