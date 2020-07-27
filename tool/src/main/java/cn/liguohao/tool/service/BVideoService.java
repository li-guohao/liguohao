package cn.liguohao.tool.service;

import java.util.List;

import cn.liguohao.tool.entity.bilibili.Video;

/**
* @ClassName: BiliVideoService.java
* @Description: BV实体类接口
* @author: 李国豪
* @date: 2020年5月10日 上午2:01:41
 */
public interface BVideoService {

	void save(Video bv);
	
	/**
	* @Title: getVideoByNumber
	* @Description: 根据AV号或BV号查询视频信息
	* @param  AV号 或 BV号
	* @return  数据库Video对象
	* @throws
	 */
	Video getVideoByNumber(String number);
	
	List<Video> findAllVideoByCountDescAndPaging(Integer curentPage, Integer pageSize);

	Integer count();
}
