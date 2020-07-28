package cn.liguohao.tool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.liguohao.tool.entity.bilibili.Bangumi;

public interface BBangummiDao extends JpaRepository<Bangumi,Long>,JpaSpecificationExecutor<Bangumi>{

	/**
	* @Title: findBangumiByKeyword
	* @Description: 根据关键词查询番剧
	* @param  参数
	* @return  返回类型
	* @throws
	 */
	Bangumi findBangumiByKeyword(String keyword);
	
}
