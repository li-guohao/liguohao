package cn.liguohao.tool.common.result.bilibili.search.bangumi;
/**
* @ClassName: Badge
* @Description: 
* 番剧条目 --> eps数组中的对象 -->  ep对象  --> badges数组 中的对象
* 番剧条目 --> badges数组 中的对象
* @author liguohao
* @date 2020年7月28日
*
 */
public class Badge {
	
	/**
	 * 	剧集标志	颜色码
	 */
	private String text;
	
	/**
	 * 文字颜色	颜色码
	 */
	private String text_color;
	
	/**
	 * 夜间文字颜色	颜色码
	 */
	private String text_color_night;
	
	/**
	 * 背景颜色
	 */
	private String bg_color;

	/**
	 * 夜间背景颜色
	 */
	private String bg_color_night;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText_color() {
		return text_color;
	}

	public void setText_color(String text_color) {
		this.text_color = text_color;
	}

	public String getText_color_night() {
		return text_color_night;
	}

	public void setText_color_night(String text_color_night) {
		this.text_color_night = text_color_night;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getBg_color_night() {
		return bg_color_night;
	}

	public void setBg_color_night(String bg_color_night) {
		this.bg_color_night = bg_color_night;
	}
	
	
	
}
