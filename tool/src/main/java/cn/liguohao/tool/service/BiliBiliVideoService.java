package cn.liguohao.tool.service;

import java.util.List;

import cn.liguohao.tool.entity.BiliVideo;

/**
* @ClassName: BiliBiliVideoService.java
* @Description: BV实体类接口
* @author: 李国豪
* @date: 2020年5月10日 上午2:01:41
 */
public interface BiliBiliVideoService {

	void save(BiliVideo bv);
	
	BiliVideo getBV(String bvnumber);
	
	List<BiliVideo> findAllBiliVideoByCountDescAndPaging(Integer curentPage, Integer pageSize);

	Integer count();
}
