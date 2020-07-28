package cn.liguohao.tool.common.result.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @ClassName: VideoOwner
* @Description: 视频UP主信息
* @author liguohao
* @date 2020年7月27日
*
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoOwner {
	
	/**
	 * UP主UID
	 */
	private Long mid;
	
	/**
	 * UP主昵称
	 */
	private String name;
	
	/**
	 * UP主头像	
	 */
	private String face;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	
	
	
}
