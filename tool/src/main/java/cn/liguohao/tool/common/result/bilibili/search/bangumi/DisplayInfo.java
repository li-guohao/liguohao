package cn.liguohao.tool.common.result.bilibili.search.bangumi;
/**
* @ClassName: DisplayInfo
* @Description: 番剧条目中的display_info数组中的对象
* @author liguohao
* @date 2020年7月28日
 */
public class DisplayInfo {
	
	/**
	 * 夜间背景颜色 颜色码
	 */
	private String bg_color_night;
	
	/**
	 * 剧集标志	
	 */
	private String text;

	/**
	 * 背景颜色	颜色码
	 */
	private String border_color;

	/**
	 * 1
	 */
	private Integer bg_style;

	/**
	 * 文字颜色	颜色码
	 */
	private String text_color;

	/**
	 * 背景颜色	颜色码
	 */
	private String bg_color;

	/**
	 * 夜间文字颜色	颜色码
	 */
	private String text_color_night;

	/**
	 * 夜间背景颜色	颜色码
	 */
	private String border_color_night;

	public String getBg_color_night() {
		return bg_color_night;
	}

	public void setBg_color_night(String bg_color_night) {
		this.bg_color_night = bg_color_night;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBorder_color() {
		return border_color;
	}

	public void setBorder_color(String border_color) {
		this.border_color = border_color;
	}

	public Integer getBg_style() {
		return bg_style;
	}

	public void setBg_style(Integer bg_style) {
		this.bg_style = bg_style;
	}

	public String getText_color() {
		return text_color;
	}

	public void setText_color(String text_color) {
		this.text_color = text_color;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getText_color_night() {
		return text_color_night;
	}

	public void setText_color_night(String text_color_night) {
		this.text_color_night = text_color_night;
	}

	public String getBorder_color_night() {
		return border_color_night;
	}

	public void setBorder_color_night(String border_color_night) {
		this.border_color_night = border_color_night;
	}
	
	
}
