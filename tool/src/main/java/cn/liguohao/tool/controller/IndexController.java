package cn.liguohao.tool.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import cn.liguohao.tool.entity.bilibili.Video;
import cn.liguohao.tool.service.BVideoService;

/**
* @ClassName: IndexController.java
* @Description: 默认首页控制层
* @author: 李国豪
* @date: 2020年6月6日 下午4:18:56
 */
@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(BiliBiliController.class);
    @Autowired
    private BVideoService bvService;
    
    @GetMapping("/")
    public String index(ModelMap model){
    	//查询热门次数
    	List<Video> VideoList = bvService.findAllVideoByCountDescAndPaging(1, 5);
    	model.addAttribute("VideoList",VideoList);
    	model.addAttribute("curentPage",1);
    	model.addAttribute("pageSize",5);
    	model.addAttribute("count",bvService.count());
    	return "index";
    }
    
    @GetMapping("/findAllByPaging")
    public String index(ModelMap model,Integer curentPage, Integer pageSize){
    	//查询热门次数
    	logger.info("curentPage: "+curentPage+"----"+"pageSize: "+pageSize);
    	List<Video> VideoList = bvService.findAllVideoByCountDescAndPaging(curentPage, pageSize);
    	model.addAttribute("VideoList",VideoList);
    	model.addAttribute("curentPage",curentPage==null?1:curentPage);
    	model.addAttribute("pageSize",pageSize==null?5:pageSize);
    	model.addAttribute("count",bvService.count());
    	return "index";
    }
}
