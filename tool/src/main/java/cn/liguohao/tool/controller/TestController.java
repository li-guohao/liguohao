package cn.liguohao.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
* @ClassName: TestController.java
* @Description: 测试控制层
* @author: 李国豪
* @date: 2020年5月9日 下午4:19:45
 */
@Controller
public class TestController {
	
	
    @GetMapping("/test")
    public String helloFreeMarker(ModelMap model){
        // 添加内容
    	return "index";
    }

}

