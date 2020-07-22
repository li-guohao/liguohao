package cn.liguohao.api.system.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

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
    private HttpClent httpClent;
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
    
    @Value("${app_ID}")
    private String appID;
    
    @Value("${app_KEY}")
    private String appKEY;
    
    @Value("${redirect_URI}")
    private String redirectURI;
    
    @Value("${authorizeURL}")
    private String authorizeURL;
    @Value("${accessTokenURL}")
    private String accessTokenURL;
    @Value("${getOpenIDURL}")
    private String getOpenIDURL;
    @Value("${getUserInfoURL}")
    private String getUserInfoURL;
    
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
        map.put("POST-用户登陆",requestUrl+"/login");
        map.put("GET-分页查询所有用户",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("POST-用户注册",requestUrl+"/save");
        map.put("Get-根据ID获取用户",requestUrl+"/info/{uid}");
        map.put("Get-跳转到QQ第三方登陆授权页面",requestUrl+"/qq/login");
        map.put("Get-QQ第三方登陆回调",requestUrl+"/qq/callback");
        // 返回结果
        return map;
    }

    @SuppressWarnings("null")
	@PostMapping("/login")
    @ResponseBody
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
    
    @RequestMapping(value = "/qq/login")
    public void qqLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        //QQ登陆第一步 重定向到授权页面
        @SuppressWarnings("deprecation")
		String callbackUrl = URLEncoder.encode(redirectURI);
        String redirectAuthorizeURL = authorizeURL 
        		+ "?response_type=code&client_id="+appID
        		+ "&redirect_uri="+callbackUrl
        		+"&state=api.liguohao.cn&scope=get_user_info";
        logger.info("QQ登陆第一步：将要重定向到授权页面："+redirectAuthorizeURL);
        response.sendRedirect(redirectAuthorizeURL);
    }
    
    @GetMapping("/qq/callback")
    private Result qqCallback(HttpServletRequest request,HttpServletResponse response){
    	Result result = new Result();
    	try {
    		// QQ登陆第二步 获取到 Authorization Code 返回第二步获取token的URL
    		String code = request.getParameter("code");
    		if(code==null || "".equals(code)) {
    			result.setMeta(new Meta(404, "未获取到QQ登陆的Authorization Code"));
    			return result;
    		}
    		
    		@SuppressWarnings("deprecation")
    		String callbackUrl = URLEncoder.encode(redirectURI); //对链接地址编码
    		if(code!=null || !"".equals(code)) {  
    			logger.info("QQ登陆第二步：已获取到Authorization Code，将要获取token");
    			String redUrl = accessTokenURL+"?grant_type=authorization_code&client_id="+ appID 
    					+ "&client_secret=" + appKEY 
    					+ "&code=" + code
						+ "&redirect_uri="+callbackUrl
						;
    			response.sendRedirect(redUrl);
    		}
    		// QQ登陆第三步 获取到Access Token 
    		String accessToken = request.getParameter("access_token");   //授权令牌，Access_Token
    		//String expiresIn = request.getParameter("expires_in");		 //该access token的有效期，单位为秒。
    		//Date expiresInDate = new Date(Long.valueOf(expiresIn)); //有效期 转化成date
    		//String refreshToken = request.getParameter("refresh_token"); //在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。 
    		if(accessToken!=null || !"".equals(accessToken)) {
    			logger.info("QQ登陆第三步 已获取到Access Token ，将要获取OpenId");
    			// 获取用户OpenID
    			String getQQOpenIdUrl = getOpenIDURL 
    					+"?access_token=" + accessToken
    					+ "&unionid=1";
    			// 因为返回的是JSON数据，不是GET请求，这里使用HttpClient请求
    			String openIdJson = httpClent.doGetHtml(getQQOpenIdUrl);
    			// 解析json数据
    			Map openIdMap = (Map) JSON.parse(openIdJson);
    			String openid = openIdMap.get("openid").toString();
    			//String unionid = (String) openIdMap.get("unionid");
    			
    			logger.info("QQ登陆第四步 已获取到OpenId ，将要获取用户信息");
    			// 取用户数据
    			String getQQUserInfoUrl = getUserInfoURL 
    					+"?access_token=" + accessToken
    					+"&oauth_consumer_key=" + appID
    					+ "&openid=" + openid;
    			String userInfoJson = httpClent.doGetHtml(getQQUserInfoUrl);
    			QQ qq = (QQ) JSON.parse(userInfoJson);
    			// 设置token和openID
    			qq.setToken(accessToken);
    			qq.setOpenID(openid);
    			// 根据openid查询数据库是否存在
    			QQ qqExist = qqService.findQQByOpenID(openid);
    			if(qqExist==null) { //此QQ未绑定任何用户
    				// 返回未绑定用户信息
    				String uid = request.getHeader("UID");
    				if(uid!=null && !"".equals(uid)) {  // 已经登陆的状态
    					// 绑定到用户
    					User user = userService.findUserByUid(Long.valueOf(uid));
    					qq.setUserEmail(user.getEmail());
    					qqService.save(qq);
    				}
    				result.setMeta(new Meta(304, "请到后台绑定QQ用户，绑定后方可使用QQ快速登陆。"));
    				return result;
    			}			
    			//已经绑定系统用户
    			// 返回系统用户信息
    			BeanUtil.copyFieldByIsExist(qq, qqExist); //讲qq中的字段属性 复制到 qqExist对象中
    			User user = userService.findUserByEmail(qqExist.getUserEmail());
    			
    			result.setMeta(new Meta(200, "QQ第三方登陆成功"));
    			result.setData(user);
    			return result;
    		}
    		
    		
    		
    		
    		
    	}catch (Exception e){
    		e.printStackTrace();
    		result.setMeta(new Meta(500,"Server Logic Exception ，User Login Faild",e.getMessage()));
    	}
    	return result;
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
            result.setData(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器异常，用户查询失败。",e.getMessage()));
        }
        return result;
    }

}
