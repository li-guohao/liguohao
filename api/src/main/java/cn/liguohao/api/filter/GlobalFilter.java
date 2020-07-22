package cn.liguohao.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;

import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.system.service.UserService;

/**
 * @ClassName: GlobalFilter
 * @Description: 全局过滤器 权限过滤、请求添加等功能
 * @author: li-guohao
 * @date: 2020-7-18 1:06:42
 */
@Component
public class GlobalFilter implements Filter{
	
	/**
	 * 日志 
	 */
    private final Logger logger = LoggerFactory.getLogger(GlobalFilter.class);

    @Autowired
    private UserService userService;
    @Value("${API_SHOW_URL}")
    private String API_SHOW_URL;
    @Value("${MODE}")
    private String MODE;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            logger.info(ResourceUtils.getURL("classpath:").getPath().substring(1));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 强行转化
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        // 请求预处理
        if("development".equals(MODE)){ //开发环境
            request.setAttribute("requestUrl",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI());
        }else {     //生产环境
            request.setAttribute("requestUrl",API_SHOW_URL);
        }
        // 设置字符集编码
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        // 跨域处理
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("ACCEPT","application/json");
        
        // 是否放行标识符
        boolean isAuth = false;

        // 校验权限
        //logger.info("权限校验过滤器 --> 运行了一次");
        
        // 版本
        logger.info("request method: "+request.getMethod());
        logger.info("request path："+request.getAttribute("requestUrl"));

        // 开发环境放行，不进行权限校验
        if("development".equals(MODE)) {
            logger.info("开发环境不进行权限校验，默认放行");
            isAuth = true;
        }else { //生产环境，进行权限校验
            // 权限校验操作
            // 根据请求方式 ，只放行GET
            String method = request.getMethod();
            if("GET".equals(method) || "OPTIONS".equals(method)) {
            	isAuth = true;
            }else {     // 不是GET请求 除了用户登陆，其它需要验证权限

                // 将请求头中的UID替换成用户在数据库的token字符
                String uid = request.getHeader("UID");
                String userToken = "";

                if(uid!=null && !"null".equals(uid)){ //有UID
                    // 根据ID查询用户token
                    userToken = userService.findUserByUid(Long.valueOf(uid)).getToken();
                }
                // 获取用户请求时的token
                String reqToken = request.getHeader("Authorization");
                if(userToken.equals(reqToken)) isAuth=true;

                // 用户登陆的POST请求放行
                int index = request.getRequestURI().indexOf("login");
                if(index>0){ //请求为用户登陆
                    isAuth = true;
                }
            }
        }
        // 根据结果处理
        if(isAuth){
            chain.doFilter(req,res);
        }else {     //返回结果JSON
            Result result = new Result();
            result.setMeta(new Meta(401,"No access"));
            res.getWriter().write(JSON.toJSONString(result));
        }
    }



    @Override
    public void destroy() {
        //logger.info("权限校验过滤器 --> 销毁了");
    }

}
