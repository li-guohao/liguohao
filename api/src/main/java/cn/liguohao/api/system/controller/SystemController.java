package cn.liguohao.api.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SystemController
 * @Description: 系统模块接口导航
 * @author: li-guohao
 * @date: 2020-7-18 1:07:19
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    /**
              * 环境，是生产环境还是开发环境
     */
    @Value("${MODE}")
    private String MODE;
    /**
     * @Title: index
     * @Description: 链接导航
     * @param request
     * @return
     * @return: Map<String,Object>
     */
    @GetMapping("")
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        if("production".equals(MODE)){ //线上(生产)环境
            requestUrl += "system";
        }
        map.put("系统模块-->用户",requestUrl+"/user");
        map.put("系统模块-->设置",requestUrl+"/option");
        // 返回结果
        return map;
    }
}
