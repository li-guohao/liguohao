package cn.liguohao.api.system.controller;

import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.system.entity.User;
import cn.liguohao.api.system.service.UserService;
import cn.liguohao.api.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Description: 用户管理
 * @author: li-guohao
 * @date: 2020-7-18 1:07:27
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 接口导航
    @GetMapping("")
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        map.put("POST-用户登陆",requestUrl+"/login");
        map.put("GET-分页查询所有用户",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("POST-用户注册",requestUrl+"/save");
        map.put("Get-根据ID获取用户",requestUrl+"/info/{uid}");
        // 返回结果
        return map;
    }

    @SuppressWarnings("null")
	@PostMapping("/login")
    private Result login(@RequestBody User user, HttpServletRequest request){
        Result result = new Result();
        try {
            User userRes = userService.login(user);
            if(userRes!=null){ //登陆成功，每次登陆更新该用户数据库的token
                userRes.setToken(UUIDUtils.getUUID64());
                //将该用户的token更新保存到数据库
                userService.save(userRes);
                result.setMeta(new Meta(200,"User Login Success"));
                result.setData(userRes);
            }else{          //登陆失败
                result.setMeta(new Meta(401,"User Login Faild，No Access Permission"));
                userRes.setPassword(null);
                result.setData(userRes);
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"Server Logic Exception ，User Login Faild",e.getMessage()));
        }
        return result;
    }


    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(HttpServletRequest request, @PathVariable Integer currentPage,@PathVariable  Integer pageSize){
        Result result = new Result();
        //空值默认
        if(currentPage==null) currentPage=1;
        if(pageSize==null) pageSize=5;
        try {
            PagingData<User> pagingData = userService.findAll(currentPage, pageSize);
            result.setMeta(new Meta(200,"Search ALL User Success"));
            result.setData(pagingData);

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"Server Logic Exception",e.getMessage()));
        }
        return result;
    }


    @PostMapping("/save")
    public Result save(@RequestBody User user){
        Result result = new Result();
        try {
            userService.save(user);
            result.setMeta(new Meta(200,"保存用户成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器异常，保存用户成功失败",e.getMessage()));
        }
        return result;
    }

    @GetMapping("/info/{uid}")
    public Result getUserByUid(@PathVariable Long uid){
        Result result = new Result();
        try {
            User user = userService.findUserByUid(uid);
            if(user!=null) {
                result.setMeta(new Meta(200,"查询用户成功"));
            }else {
                result.setMeta(new Meta(404,"查询用户失败，此用户不存在"));
            }
            result.setData(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器异常，用户查询失败。",e.getMessage()));
        }
        return result;
    }

}
