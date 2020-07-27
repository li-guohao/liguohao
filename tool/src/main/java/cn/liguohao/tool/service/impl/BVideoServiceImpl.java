package cn.liguohao.tool.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.liguohao.tool.common.result.bilibili.Result;
import cn.liguohao.tool.common.util.HttpUtils;
import cn.liguohao.tool.dao.BVDao;
import cn.liguohao.tool.entity.bilibili.Video;
import cn.liguohao.tool.service.BVideoService;

@Service
@Transactional
public class BVideoServiceImpl implements BVideoService {

	@Autowired
	private BVDao bvDao;
	@Value("${BiliBliSearchVideoApi}")
    private String BiliBliSearchVideoApi;
	private static final Logger logger = LoggerFactory.getLogger(BVideoServiceImpl.class);
	
	@Override
	public void save(Video bv) {
		bvDao.save(bv);
	}

	@Override
	public Video getVideoByNumber(String number) {
		
		String bvnum = null;
		Long avnum = null;
		Video video = null;
		String result = null;
		List<Video> listSearch = null;
		
		// 判断是AV号还是BV号
		if(number.startsWith("bv") || number.startsWith("BV")) { //BV号
			bvnum = number;
			//判断此ID是否已经查询
			listSearch = bvDao.findVideoByBvid(bvnum);
		}else if(number.startsWith("av" ) || number.startsWith("AV")) { //AV号
			avnum = Long.valueOf(number.substring("av".length()));
			//判断此ID是否已经查询
			listSearch = bvDao.findVideoByAid(avnum);
		}else {
			throw new RuntimeException("未知的视频号码格式，请用BV号和AV号");
		}
		
		
		logger.info("查询的结果数为："+listSearch.size());
		if(!listSearch.isEmpty()){ // 代表已经查询过了
			//增加一次查询次数
			Video Video = listSearch.get(0);
			Video.setSearchCount(Video.getSearchCount()+1);
			//保存更改到数据库
			save(Video);
			//直接返回数据库的信息
			return Video;
		}
		// 没有查询过 则 新建储存对象查询并保存
    	Video bv = new Video();
    	
		
    	
    	// 调用接口获取数据
		if(avnum != null) {
			result = HttpUtils.doGet(BiliBliSearchVideoApi+"?aid="+avnum);
		}else if(bvnum != null) {
			result = HttpUtils.doGet(BiliBliSearchVideoApi+"?bvid="+bvnum);
		}else {
			throw new RuntimeException("未获取到AV号或BV号，可能未输入");
		}
		
		// 转化成Java对象
		Result resultObjTemp = JSON.parseObject(result,Result.class);
		video = JSONObject.toJavaObject((JSONObject)resultObjTemp.getData(), Video.class);
		
		// 复制属性值到 数据库的视频对象 这里可能会出异常
		BeanUtils.copyProperties(video, bv);
		bv.setSearchCount(1); //设置新查询的为1次
		
    	// 保存到数据库
    	bvDao.save(bv);
		return bv;
	}

	@Override
	public List<Video> findAllVideoByCountDescAndPaging(Integer curentPage, Integer pageSize) {
		//空处理
		if(curentPage==null) curentPage=1;
		if(pageSize==null) pageSize=5;
		//查询
		return bvDao.findAllVideoByCountDescAndPaging((curentPage-1)*pageSize, pageSize);
	}

	@Override
	public Integer count() {
		Long count = bvDao.count();
		return count.intValue();
	}

}
