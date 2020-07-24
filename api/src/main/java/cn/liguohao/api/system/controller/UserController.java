package cn.liguohao.api.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.system.client.HttpClent;
import cn.liguohao.api.system.entity.QQ;
import cn.liguohao.api.system.entity.User;
import cn.liguohao.api.system.service.QQService;
import cn.liguohao.api.system.service.UserService;
import cn.liguohao.api.utils.BeanUtil;
import cn.liguohao.api.utils.UUIDUtils;

/**
 * @ClassName: UserController
 * @Description: 用户管理
 * @author: li-guohao
 * @date: 2020-7-18 1:07:27
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QQService qqService;
    
    /**
	 * 日志 
	 */
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    
    /**
     * 环境，是生产环境还是开发环境
     */
    @Value("${MODE}")
    private String MODE;
    @Value("${QQ_USER_EMAIL}")
    private String qqUserEmail;
    
    
    // 接口导航
    @GetMapping("")
    @ResponseBody
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        if("production".equals(MODE)){ //线上(生产)环境
            requestUrl += "system/user";
        }
        //map.put("POST-用户登陆",requestUrl+"/login");
        map.put("GET-分页查询所有用户",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("POST-用户注册",requestUrl+"/save");
        map.put("Get-根据ID获取用户",requestUrl+"/info/{uid}");
        map.put("Get-跳转到QQ第三方登陆授权页面",requestUrl+"/qq/login");
        map.put("Get-QQ第三方登陆回调",requestUrl+"/qq/callback");
        // 返回结果
        return map;
    }

//    @SuppressWarnings("null")
//	@PostMapping("/login")
//    @ResponseBody
//    private Result login(@RequestBody User user, HttpServletRequest request){
//        Result result = new Result();
//        try {
//            User userRes = userService.login(user);
//            if(userRes!=null){ //登陆成功，每次登陆更新该用户数据库的token
//                userRes.setToken(UUIDUtils.getUUID64());
//                //将该用户的token更新保存到数据库
//                userService.save(userRes);
//                result.setMeta(new Meta(200,"User Login Success"));
//                result.setData(userRes);
//            }else{          //登陆失败
//                result.setMeta(new Meta(401,"User Login Faild，No Access Permission"));
//                userRes.setPassword(null);
//                result.setData(userRes);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setMeta(new Meta(500,"Server Logic Exception ，User Login Faild",e.getMessage()));
//        }
//        return result;
//    }
    
    @RequestMapping(value = "/qq/login")
    public void qqLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        //QQ登陆第一步 重定向到授权页面
        try {
        	String authorizeURL = new Oauth().getAuthorizeURL(request);
            response.sendRedirect(authorizeURL);//将页面重定向到qq第三方的登录页面
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping("/qq/callback")
    private String qqCallback(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws IOException{
    	// 新建一个QQ用户 
    	QQ qq = new QQ();
    	
    	logger.info("AfterLogin=======================================================");
        response.setContentType("text/html; charset=utf-8");  // 响应编码
     
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();//code
            
            logger.info(parameterName+":"+request.getParameter(parameterName));//state
        }
        String state = request.getSession().getAttribute("qq_connect_state").toString();
        logger.info("qq_connect_state:"+state);
     
        try {
            // 获取AccessToken(AccessToken用于获取OppendID)
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            logger.info("accessTokenObj:"+accessTokenObj);
            // 用于接收AccessToken
            String accessToken   = null,
                    openID        = null;
            long tokenExpireIn = 0L; // AccessToken有效时长
     
            if (accessTokenObj.getAccessToken().equals("")) {
                //                我们的网站被CSRF攻击了或者用户取消了授权
                //                做一些数据统计工作
            	logger.info("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();  // 获取AccessToken
                tokenExpireIn = accessTokenObj.getExpireIn();
                logger.info("demo_access_token", accessToken);
                logger.info("demo_token_expirein", String.valueOf(tokenExpireIn));
                qq.setToken(accessToken);
     
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                // 通过对象获取[OpendId]（OpendID用于获取QQ登录用户的信息）
                openID = openIDObj.getUserOpenID();
     
                logger.info("欢迎你，代号为 " + openID + " 的用户!");
                logger.info("demo_openid", openID);
                qq.setOpenID(openID);
                // 利用获取到的accessToken 去获取当前用户的openid --------- end
     
                logger.info("start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start ");
                // 通过OpenID获取QQ用户登录信息对象(Oppen_ID代表着QQ用户的唯一标识)
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                // 获取用户信息对象(只获取nickename与Gender)
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean.getRet() == 0) {
                	logger.info("昵称："+userInfoBean.getNickname() );
                	qq.setNickname(userInfoBean.getNickname());
                	logger.info("性别："+userInfoBean.getGender() );
                	qq.setGender(userInfoBean.getGender() );
                	logger.info("黄钻等级： " + userInfoBean.getLevel() );
                	logger.info("会员 : " + userInfoBean.isVip());
                	logger.info("黄钻会员： " + userInfoBean.isYellowYearVip());
                	logger.info("AvatarURL30=" + userInfoBean.getAvatar().getAvatarURL30());
                	qq.setFigureurl(userInfoBean.getAvatar().getAvatarURL30());
                	logger.info("AvatarURL50=" + userInfoBean.getAvatar().getAvatarURL50());
                	qq.setFigureurl_1(userInfoBean.getAvatar().getAvatarURL50());
                	logger.info("AvatarURL100=" + userInfoBean.getAvatar().getAvatarURL100());
                	qq.setFigureurl_2(userInfoBean.getAvatar().getAvatarURL100());
                } else {
                	logger.info("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                }
                logger.info("end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end ");
     
                QQ qqDateBase = qqService.findQQByOpenID(openID);
                // 根据传递的请求头UID判断
                if(qqDateBase==null || "".equals(qqDateBase)) { // 数据库不存在
                	// 判断此邮箱是否已经绑定了QQ用户
                	QQ qqExist = qqService.findQQByUserEmail(qqUserEmail);
                	if(qqExist!=null) { //已经绑定了QQ用户
                		map.addAttribute("msg","非常抱歉，此网站不支持用户注册，目前仅支持单用户");
                		map.addAttribute("domain","https://liguohao.cn/login");
                	}else { //为绑定管理猿QQ用户
                		qq.setUserEmail(qqUserEmail);
                		qqService.save(qq);
                		User user = userService.findUserByEmail(qqUserEmail);
                		String uidAndToken = user.getUid() + "&" + user.getToken();
                		map.addAttribute("uidAndToken",uidAndToken);  //返回格式： UID&token
                		map.addAttribute("msg","绑定成功，请稍后");
                		map.addAttribute("domain","https://liguohao.cn/login");
                	}
                }else { //非绑定操作
                	// 快速登陆操作
                	// 根据OpenID查询数据库
                	QQ qqExist = qqService.findQQByOpenID(qq.getOpenID());
                	if(qqExist!=null) { // 已经绑定
                		// 获取用户信息
                		User user = userService.findUserByEmail(qqUserEmail); 
                		String uidAndToken = user.getUid() + "&" + user.getToken(); 
                		map.addAttribute("uidAndToken",uidAndToken);
                		map.addAttribute("msg","登陆成功，请稍后");
                		map.addAttribute("domain","https://liguohao.cn/login");
                	}else { //未绑定
                		map.addAttribute("msg","未绑定QQ，无法快速登陆");
                	}
                }
               
               
            }
        } catch (QQConnectException e) {
        	e.printStackTrace();
        }
        if(map.getAttribute("user")==null) map.addAttribute("user", "");
        if(map.getAttribute("domain")==null) map.addAttribute("domain", "");
        if(map.getAttribute("msg")==null) map.addAttribute("msg", "");
        return "qqLogin";
    }


    @GetMapping("/list/{currentPage}/{pageSize}")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public Result getUserByUid(@PathVariable Long uid){
        Result result = new Result();
        try {
            User user = userService.findUserByUid(uid);
            if(user!=null) {
                result.setMeta(new Meta(200,"查询用户成功"));
            }else {
                result.setMeta(new Meta(404,"查询用户失败，此用户不存在"));
            }
            User user2 = new User();
            BeanUtil.copyFieldByIsExist(user, user2);
            user2.setPassword("");
            result.setData(user2);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器异常，用户查询失败。",e.getMessage()));
        }
        return result;
    }

}
