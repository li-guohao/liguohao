package cn.liguohao.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: IndexController
 * @Description: 链接导航
 * @author: li-guohao
 * @date: 2020-7-18 1:00:46
 */
@RestController
public class IndexController {

    @Value("${API_MD_URL}")
    private String API_MD_URL; //接口文档地址

    @GetMapping("")
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        map.put("注意","除了GET请求外，其它所有的请求方式都需要验证权限");
        map.put("文件模块",requestUrl+"file");                  // 文件模块
        map.put("博客模块",requestUrl+"blog");                  // 博客模块
        map.put("系统模块,需要权限校验",requestUrl+"system");   // 系统模块
        map.put("接口文档",API_MD_URL);                         // markdown接口文档地址
        // 返回结果
        return map;
    }

}
