package cn.liguohao.tool.common.result.bilibili.search.bangumi;

import java.util.List;

/**
* @ClassName: Ep
* @Description: 番剧条目中的eps数组中的对象
* @author liguohao
* @date 2020年7月28日
 */
public class Ep {

	/**
	 * 分集epID
	 */
	private Long id;
	
	/**
	 * 分集封面url	
	 */
	private String cover;
	
	/**
	 * 完整标题	
	 */
	private String title;
	
	/**
	 * 分集重定向url	
	 */
	private String url;
	
	private String release_date;
	
	/**
	 * 分集标志	
	 */
	private List<Badge> badges;
	
	/**
	 * 短标题
	 */
	private String index_title;
	
	/**
	 * 单集标题
	 */
	private String long_title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	public String getIndex_title() {
		return index_title;
	}

	public void setIndex_title(String index_title) {
		this.index_title = index_title;
	}

	public String getLong_title() {
		return long_title;
	}

	public void setLong_title(String long_title) {
		this.long_title = long_title;
	}
	
	
	
}
