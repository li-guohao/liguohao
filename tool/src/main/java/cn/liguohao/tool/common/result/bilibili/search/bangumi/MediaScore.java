package cn.liguohao.tool.common.result.bilibili.search.bangumi;
/**
* @ClassName: MediaScore
* @Description: 番剧条目中的media_score对象
* @author liguohao
* @date 2020年7月28日
 */
public class MediaScore {

	/**
	 * 总计评分人数
	 */
	private Integer user_count;
	
	/**
	 * 评分
	 */
	private Integer score;

	public Integer getUser_count() {
		return user_count;
	}

	public void setUser_count(Integer user_count) {
		this.user_count = user_count;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
}
