package cn.liguohao.api.blog.controller;

import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.blog.entity.Link;
import cn.liguohao.api.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LinkController
 * @Description: 导航栏
 * @author: li-guohao
 * @date: 2020-7-18 1:01:38
 */
@RestController
@RequestMapping("/blog/link")
public class LinkController {

    @Autowired
    private LinkService linkService;
    /**
     * 环境，是生产环境还是开发环境
     */
    @Value("${MODE}")
    private String MODE;

    // 接口导航
    @GetMapping("")
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        if("production".equals(MODE)){ //线上(生产)环境
            requestUrl += "blog/link";
        }
        map.put("GET-查询所有链接",requestUrl+"/list");
        map.put("POST-添加链接",requestUrl+"/save");
        map.put("GET-根据ID查询链接",requestUrl+"/{lid}");
        map.put("DELETE-根据ID删除链接",requestUrl+"/{lid}");
        // 返回结果
        return map;
    }

    @GetMapping("/list")
    public Result findAll(){
        Result result = new Result();
        try{
            List<Link> linkList = linkService.findAll();
            result.setMeta(new Meta(200,"查询成功！！"));
            result.setData(linkList);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"查询失败，服务器内部错误"));
        }
        return result;
    }

    @PostMapping("/save")
    public Result add(@RequestBody Link link){
        Result result = new Result();
        try{
            Link linkRes = linkService.save(link);
            result.setMeta(new Meta(200,"添加成功！！"));
            result.setData(linkRes);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"添加失败，服务器内部错误"));
        }
        return result;
    }

    @GetMapping("/{lid}")
    public Result findOneByLid(@PathVariable Long lid){
        Result result = new Result();
        try{
            Link link = linkService.findOneByLid(lid);
            if(link!=null){
                result.setMeta(new Meta(200,"查询成功！！"));
            }else {
                result.setMeta(new Meta(404,"抱歉，没有查到此链接。"));
            }
            result.setData(link);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"查询失败，服务器内部错误",e.getMessage()));
        }
        return result;
    }
    @DeleteMapping("/{lid}")
    public Result deleteOneByLid(@PathVariable Long lid){
        Result result = new Result();
        try{
            linkService.deleteOneByLid(lid);
            result.setMeta(new Meta(200,"删除成功！！"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"删除失败，服务器内部错误",e.getMessage()));
        }
        return result;
    }
}
