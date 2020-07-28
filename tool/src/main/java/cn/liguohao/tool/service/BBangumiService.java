package cn.liguohao.tool.service;

import cn.liguohao.tool.entity.bilibili.Bangumi;

/**
* @ClassName: BBangumiService
* @Description: 哔哩哔哩番剧
* @author liguohao
* @date 2020年7月28日
 */
public interface BBangumiService {

	/**
	* @Title: findBangmiByKeyword
	* @Description: 根据关键词查找番剧
	* @param  关键词
	* @return  番剧对象
	* @throws
	 */
	Bangumi findBangmiByKeyword(String keyword);
	
}
