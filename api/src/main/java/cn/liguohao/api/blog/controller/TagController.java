package cn.liguohao.api.blog.controller;

import cn.liguohao.api.blog.entity.Tag;
import cn.liguohao.api.blog.service.TagService;
import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TagController
 * @Description: 标签
 * @author: li-guohao
 * @date: 2020-7-18 1:01:49
 */
@RestController
@RequestMapping("/blog/tag")
public class TagController {

    @Autowired
    private TagService tagService;
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
            requestUrl += "blog/tag";
        }
        map.put("POST-添加标签",requestUrl+"/save");
        map.put("GET-分页查询所有标签",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("GET-根据ID查询标签",requestUrl+"/{tid}");
        map.put("PUT-更新标签",requestUrl+"/update");
        map.put("DELETE-根据ID删除标签",requestUrl+"/{tid}");
        map.put("DELETE-删除未被引用的标签",requestUrl+"/deleteByNotUse");
        // 返回结果
        return map;
    }

    @PostMapping("/save")
    public Result add(HttpServletRequest request, @RequestBody Tag tag){
        Result result  = new Result();
        try {
            tagService.save(tag);
            result.setMeta(new Meta(200,"保存标签成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(HttpServletRequest request, @PathVariable Integer currentPage, @PathVariable  Integer pageSize) {
        Result result = new Result();
        try {
            PagingData<Tag> pagingData = tagService.findAllByPaging(currentPage,pageSize);
            result.setMeta(new Meta(200,"查询所有标签成功"));
            result.setData(pagingData);

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/{tid}")
    public Result findOneByAid(HttpServletRequest  request, @PathVariable Long tid){
        Result result = new Result();
        try {
            Tag tag = tagService.findTagByAid(tid);
            if(tag!=null){
                result.setData(tag);
                result.setMeta(new Meta(200,"查询标签成功"));
            }else{
                result.setMeta(new Meta(404,"请求的标签不存在"));
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }



    @PutMapping("/update")
    public Result update(HttpServletRequest  request,@RequestBody Tag tag) {
        Result result  = new Result();
        try {
            tagService.mergeTag(tag);
            result.setMeta(new Meta(200,"更新标签成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }

    @DeleteMapping("/{tid}")
    public Result deleteOneByAid(HttpServletRequest  request, @PathVariable Long tid){
        Result result = new Result();
        try {
            tagService.deleteTagByAid(tid);
            result.setMeta(new Meta(200,"删除标签成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }

    @DeleteMapping("/deleteByNotUse")
    public Result deleteByNotUse(){
        Result result = new Result();
        try {
            tagService.deleteByNotUse();
            result.setMeta(new Meta(200,"删除未被引用标签成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常",e.getMessage()));
        }
        return result;
    }

}
