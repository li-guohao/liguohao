package cn.liguohao.tool.common.result.bilibili;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
* @ClassName: Result
* @Description: 哔哩哔哩返回结果对象
* @author liguohao
* @date 2020年7月27日
*
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	/**
	 * 返回值:
	 *   0：成功
	 *  -400：请求错误
	 *  -404：无视频
	 */
	private Integer code;
	/**
	 * 错误信息 默认为0
	 */
	private String message;
	/**
	 * 1
	 */
	private Integer ttl;
	/**
	 * 信息本体
	 */
	private Object data;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getTtl() {
		return ttl;
	}
	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
