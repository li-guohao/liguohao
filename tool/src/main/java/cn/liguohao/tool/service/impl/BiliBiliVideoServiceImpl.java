package cn.liguohao.tool.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.liguohao.tool.common.util.HtmlUnitUtils;
import cn.liguohao.tool.dao.BVDao;
import cn.liguohao.tool.entity.BiliVideo;
import cn.liguohao.tool.service.BiliBiliVideoService;

@Service
@Transactional
public class BiliBiliVideoServiceImpl implements BiliBiliVideoService {

	@Autowired
	private BVDao bvDao;
	@Value("${BiliBliSearchVideoApi}")
    private String BiliBliSearchVideoApi;
	private static final Logger logger = LoggerFactory.getLogger(BiliBiliVideoServiceImpl.class);
	
	@Override
	public void save(BiliVideo bv) {
		bvDao.save(bv);
	}

	@Override
	public BiliVideo getBV(String bvnumber) {
		//判断此ID是否已经查询
		List<BiliVideo> listSearch = bvDao.findBiliVideoByBvid(bvnumber);
		logger.info("查询的结果数为："+listSearch.size());
		if(!listSearch.isEmpty()){ // 代表已经查询过了
			//增加一次查询次数
			BiliVideo biliVideo = listSearch.get(0);
			biliVideo.setSearchCount(biliVideo.getSearchCount()+1);
			//保存更改到数据库
			save(biliVideo);
			//直接返回数据库的信息
			return biliVideo;
		}
		// 没有查询过 则 新建储存对象查询并保存
    	BiliVideo bv = new BiliVideo();
    	bv.setSearchCount(1); //设置新查询的为1次
    	bv.setBvid(bvnumber);
    	
    	// 获取页面信息
    	Element element = HtmlUnitUtils.getElementByIndexInElements(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item", 0);
    	
        //获取封面URL
    	//String urlPart = HtmlUnitUtils.getValueByElementAttr(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item .img-anchor .lazy-img img", 0, "src");
    	String urlPart = element.select(".img-anchor .lazy-img img").get(0).attr("src");
    	String url = "http:" + urlPart.substring(0, urlPart.indexOf("@"));
    	logger.info("获取到的图片Url:"+url);
    	bv.setCoverImgUrl(url);
    	// 获取视频标题
    	//String title = HtmlUnitUtils.getValueByElementAttr(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item .info .headline a", 0, "title");
    	String title = element.select(".info .headline a").get(0).attr("title");
    	bv.setTitle(title);
    	bv.setSerachDate(new Date());
    	// 保存到数据库
    	bvDao.save(bv);
		return bv;
	}

	@Override
	public List<BiliVideo> findAllBiliVideoByCountDescAndPaging(Integer curentPage, Integer pageSize) {
		//空处理
		if(curentPage==null) curentPage=1;
		if(pageSize==null) pageSize=5;
		//查询
		return bvDao.findAllBiliVideoByCountDescAndPaging((curentPage-1)*pageSize, pageSize);
	}

	@Override
	public Integer count() {
		Long count = bvDao.count();
		return count.intValue();
	}

}
