package cn.liguohao.api.blog.controller;

import cn.liguohao.api.blog.entity.Comment;
import cn.liguohao.api.blog.service.CommentService;
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
 * @ClassName: CommentController
 * @Description: 评论转发层
 * @author: li-guohao
 * @date: 2020-7-18 1:01:27
 */
@RestController
@RequestMapping(value = "/blog/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
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
            requestUrl += "/blog/comment";
        }
        map.put("POST-添加评论",requestUrl+"/save");
        map.put("GET-查询所有评论",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("GET-根据ID查询评论",requestUrl+"/{cid}");
        map.put("PUT-更新评论",requestUrl+"/update");
        map.put("DELETE-根据ID删除评论",requestUrl+"/{cid}");
        // 返回结果
        return map;
    }

    @PostMapping("/save")
    public Result add(@RequestBody Comment comment){
        Result result = new Result();
        try {
            commentService.save(comment);
            result.setData(comment);
            result.setMeta(new Meta(200,"保存评论成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"操作失败，后台接口异常，服务器内部错误！",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        Result result = new Result();
        try {
            PagingData<Comment> pagingData = commentService.findAllByPaging(currentPage, pageSize);
            result.setData(pagingData);
            result.setMeta(new Meta(200,"查询评论成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"操作失败，后台接口异常，服务器内部错误！",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/{cid}")
    public Result findOneByCid(@PathVariable Long cid) {
        Result result = new Result();
        try {
            Comment comment = commentService.findCommentByCid(cid);
            result.setData(comment);
            if(comment==null) {
                result.setMeta(new Meta(404,"查询无评论数据"));
            }else {
                result.setMeta(new Meta(200,"查询评论成功"));
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"操作失败，后台接口异常，服务器内部错误！",e.getMessage()));
        }
        return result;
    }


    @PutMapping("/update")
    public Result update(@RequestBody Comment comment) {
        Result result = new Result();
        try {
            Comment resComment = commentService.updataComment(comment);
            result.setData(resComment);
            result.setMeta(new Meta(200,"更新评论成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"操作失败，后台接口异常，服务器内部错误！",e.getMessage()));
        }
        return result;
    }


    @DeleteMapping("/{cid}")
    public Result deleteOneByCid(@PathVariable Long cid) {
        Result result = new Result();
        try {
            commentService.deleteCommentByCid(cid);
            result.setMeta(new Meta(200,"删除评论成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"操作失败，后台接口异常，服务器内部错误！",e.getMessage()));
        }
        return result;
    }

}
