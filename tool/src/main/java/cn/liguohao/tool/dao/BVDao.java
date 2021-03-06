package cn.liguohao.tool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.liguohao.tool.entity.bilibili.Video;

public interface BVDao extends JpaRepository<Video,Long>,JpaSpecificationExecutor<Video>{

	@Query(value = "select * from bili_video where bvid=?1", nativeQuery = true)
	List<Video> findVideoByBvid(String bvid);
	
	@Query(value = "select * from bili_video where aid=?1", nativeQuery = true)
	List<Video> findVideoByAid(Long aid);
	
	
	/**
	* @Description: 根据查询次数从多到少分页查询
	* @param:curentPage-当前页(sql是从0开始) pageSize-查询条数
	* @return：集合
	* @throws：无
	* @date: 2020年6月6日 下午4:12:26
	 */
	@Query(value = "select * from bili_video order by search_count desc limit ?1,?2", nativeQuery = true)
	List<Video> findAllVideoByCountDescAndPaging(Integer curentPage, Integer pageSize);
	
	
}
