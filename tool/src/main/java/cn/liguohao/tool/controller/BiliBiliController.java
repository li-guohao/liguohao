package cn.liguohao.tool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.liguohao.tool.entity.BiliVideo;
import cn.liguohao.tool.service.BiliBiliVideoService;

/**
* @ClassName: BiliBiliController.java
* @Description: 爬取B站信息相关Controller
* @author: 李国豪
* @date: 2020年5月9日 下午5:53:58
 */
@Controller
@RequestMapping("/bilibili")
public class BiliBiliController {

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BiliBiliController.class);
    @Value("${BiliBliSearchVideoApi}")
    private String BiliBliSearchVideoApi;
    @Autowired
    private BiliBiliVideoService bvService;
    
    
    @GetMapping("/getCover")
    public String getCover(ModelMap model, String bvnumber){
    	if(bvnumber==null || "".equals(bvnumber)) bvnumber="BV1vz4y1R7GR";
//    	// 新建储存对象
//    	BiliVideo bv = new BiliVideo();
//    	bv.setBvid(bvnumber);
//    	
//    	// 获取页面信息
//    	Element element = HtmlUnitUtils.getElementByIndexInElements(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item", 0);
//    	
//        //获取封面URL
//    	//String urlPart = HtmlUnitUtils.getValueByElementAttr(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item .img-anchor .lazy-img img", 0, "src");
//    	String urlPart = element.select(".img-anchor .lazy-img img").get(0).attr("src");
//    	String url = "http:" + urlPart.substring(0, urlPart.indexOf("@"));
//    	logger.info("获取到的图片Url:"+url);
//    	bv.setCoverImgUrl(url);
//    	// 获取视频标题
//    	//String title = HtmlUnitUtils.getValueByElementAttr(BiliBliSearchVideoApi+bvnumber, ".video-list .video-item .info .headline a", 0, "title");
//    	String title = element.select(".info .headline a").get(0).attr("title");
//    	bv.setTitle(title);
//    	bv.setSerachDate(new Date());
//    	// 保存到数据库
//    	bvService.save(bv);
    	BiliVideo bv = bvService.getBV(bvnumber);
    	// 返回结果到页面
    	model.addAttribute("BV", bv);
    	return "bilibili/searchResult";
    }

}
