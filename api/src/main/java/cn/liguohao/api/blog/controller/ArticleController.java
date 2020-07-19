package cn.liguohao.api.blog.controller;

import cn.liguohao.api.blog.entity.Article;
import cn.liguohao.api.blog.service.ArticleService;
import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.system.entity.User;
import cn.liguohao.api.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ArticleController
 * @Description: 文章管理
 * @author: li-guohao
 * @date: 2020-7-18 1:01:02
 */
@RestController
@RequestMapping("/blog/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    /**
     * 是否为测试环境
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
            requestUrl += "/blog/article";
        }
        map.put("POST-添加",requestUrl+"/save");
        map.put("GET-分页查询",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("GET-根据文章ID查询文章",requestUrl+"/{aid}");
        map.put("GET-根据文章ID查询文章",requestUrl+"/{aid}");
        map.put("PUT-更新文章",requestUrl+"/update");
        map.put("PUT-置顶文章  如已经存在置顶文章则置顶失败则返回304-未修改",requestUrl+"/top/{aid}");
        map.put("DELETE-根据ID逻辑删除文章",requestUrl+"/{aid}");
        map.put("GET-查询指定数量最新文章",requestUrl+"/newest/{number}");
        map.put("GET-查询指定数量热门文章",requestUrl+"/hottest/{number}");
        map.put("GET-根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列, 最新的在前面",requestUrl+"/list/{currentPage}/{pageSize}/{tid}");
        map.put("GET-根据文章标题关键词模糊查询文章",requestUrl+"/list/like/{currentPage}/{pageSize}/{titlePart}");
        map.put("GET-前台根据文章标题关键词模糊查询文章",requestUrl+"/list/open/like/{currentPage}/{pageSize}/{titlePart}");
        map.put("GET-输入空文章标题关键词时模糊查询文章",requestUrl+"/list/like/{currentPage}/{pageSize}");
        // 返回结果
        return map;
    }

    @PostMapping("/save")
    public Result add(HttpServletRequest request, @RequestBody Article article){
        Result result  = new Result();
        try {
            // 设置文章作者
            String uid = request.getHeader("UID");
            User user = userService.findUserByUid(Long.valueOf(uid));
            article.setAuthor(user.getNickname());
            // 添加文章
            articleService.save(article);
            result.setMeta(new Meta(200,"保存文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，保存文章失败",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(HttpServletRequest request, @PathVariable Integer currentPage, @PathVariable  Integer pageSize) {
        Result result = new Result();
        try {
            PagingData<Article> pagingData = articleService.findAllByPaging(currentPage, pageSize);
            result.setMeta(new Meta(200,"查询所有文章成功"));
            result.setData(pagingData);

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询所有文章失败",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/{aid}")
    public Result findOneByAid(@PathVariable Long aid){
        Result result = new Result();
        try {
            Article article = articleService.findArticleByAid(aid);
            if(article!=null){
                result.setData(article);
                result.setMeta(new Meta(200,"查询文章成功"));
            }else{
                result.setMeta(new Meta(404,"查询的文章不存在"));
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询文章失败",e.getMessage()));
        }
        return result;
    }



    @PutMapping("/update")
    public Result update(@RequestBody Article article) {
        Result result  = new Result();
        try {
            articleService.mergeArticle(article);
            result.setMeta(new Meta(200,"更新文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，更新文章失败",e.getMessage()));
        }
        return result;
    }

    @DeleteMapping("/{aid}")
    public Result deleteOneByAid(@PathVariable Long aid){
        Result result = new Result();
        try {
            articleService.deleteArticleByAid(aid);
            result.setMeta(new Meta(200,"删除文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，删除文章失败",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/newest/{number}")
    public Result findNewest(@PathVariable Integer number){
        Result result = new Result();
        try {
            List<Article> articleList = articleService.findArticlesOrderByUpdateTime(number);
            result.setData(articleList);
            result.setMeta(new Meta(200,"查询最新文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询最新文章失败",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/hottest/{number}")
    public Result findHotArticles(@PathVariable Integer number){
        Result result = new Result();
        try {
            List<Article> articleList = articleService.findHotArticles(number);
            result.setData(articleList);
            result.setMeta(new Meta(200,"查询热门文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询热门文章失败",e.getMessage()));
        }
        return result;
    }

    // 根据tid查询所有包含该标签的文章数据，按照更新时间顺序排列, 最新的在前面
    @GetMapping("/list/{currentPage}/{pageSize}/{tid}")
    public Result findAll(HttpServletRequest request, @PathVariable Integer currentPage,
                          @PathVariable  Integer pageSize, @PathVariable Long tid) {
        Result result = new Result();
        try {
            PagingData<Article> pagingData = articleService.findArticlesContainTidOrderByUpdateTimeDesc(currentPage,pageSize,tid);
            result.setData(pagingData);
            result.setMeta(new Meta(200,"查询包含此标签的文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询包含此标签的文章失败",e.getMessage()));
        }
        return result;
    }

    // 后台根据文章标题关键词模糊查询文章
    @GetMapping("/list/like/{currentPage}/{pageSize}/{titlePart}")
    public Result findAllLikeTitle(HttpServletRequest request, @PathVariable Integer currentPage,
                          @PathVariable  Integer pageSize, @PathVariable String titlePart) throws UnsupportedEncodingException {
        Result result = new Result();
        try {
            PagingData<Article> pagingData = articleService.findArticlePagingWhereTitleLike(currentPage,pageSize,titlePart);
            result.setData(pagingData);
            result.setMeta(new Meta(200,"查询包含此标题片段的文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询包含此标题片段的文章失败",e.getMessage()));
        }
        return result;
    }
    
    // 前台根据文章标题关键词模糊查询文章 findAllLikeTitleAndIsOpen
    @GetMapping("/list/open/like/{currentPage}/{pageSize}/{titlePart}")
    public Result findAllLikeTitleAndIsOpen(HttpServletRequest request, @PathVariable Integer currentPage,
                          @PathVariable  Integer pageSize, @PathVariable String titlePart) throws UnsupportedEncodingException {
       Result result = new Result();
        try {
            PagingData<Article> pagingData = articleService.findArticlePagingWhereTitleLikeAndIsOpen(currentPage,pageSize,titlePart,1);
            result.setData(pagingData);
            result.setMeta(new Meta(200,"查询包含此标题片段的文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询包含此标题片段的文章失败",e.getMessage()));
        }
        return result;
    }
    
    // 输入空文章标题关键词时模糊查询文章
    @GetMapping("/list/like/{currentPage}/{pageSize}")
    public Result findAllLikeTitle(HttpServletRequest request, @PathVariable Integer currentPage,
                          @PathVariable  Integer pageSize) throws UnsupportedEncodingException {
        Result result = new Result();
        try {
            PagingData<Article> pagingData = articleService.findArticlePagingWhereTitleLike(currentPage,pageSize,"");
            result.setData(pagingData);
            result.setMeta(new Meta(200,"查询包含此标题片段的文章成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，查询包含此标题片段的文章失败",e.getMessage()));
        }
        return result;
    }

    
    // 置顶文章  如已经存在置顶文章则置顶失败则返回304-未修改
    @PutMapping("/top/{aid}")
    public Result topArticleByAid(@PathVariable Long aid) {
    	Result result = new Result();
        try {
            Article article = articleService.topArticleByAid(aid);
            if((article != null )) {
            	if(article.getAid()==aid) { //取消置顶操作
            		result.setMeta(new Meta(200,"取消置顶成功"));
            	}else {
            		result.setMeta(new Meta(304,"置顶失败，已经存在被置顶的文章："+article.getTitle()));
            	}
            }else {
            	result.setMeta(new Meta(200,"置顶成功"));
            }
            result.setData(article);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部异常，置顶文章失败",e.getMessage()));
        }
        return result;
    }
}
