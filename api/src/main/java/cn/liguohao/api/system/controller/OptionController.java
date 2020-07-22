package cn.liguohao.api.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;
import cn.liguohao.api.system.entity.Option;
import cn.liguohao.api.system.service.OptionService;

/**
 * @ClassName: OptionController
 * @Description: 系统设置
 * @author: li-guohao
 * @date: 2020-7-18 8:52:02
 */
@RestController
@RequestMapping("/system/option")
public class OptionController {
	
	@Autowired
	private OptionService optionService;
	
	/**
     * 环境，是生产环境还是开发环境
     */
    @Value("${MODE}")
    private String MODE;
	
	/**
	 * @Title: index
	 * @Description: 接口导航
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
            requestUrl += "system/option";
        }
        map.put("GET-根据分类查询所有系统设置",requestUrl+"/list/category/{optionCategory}");
        map.put("GET-根据设置的分类和名称查询系统设置",requestUrl+"/one/{optionCategory}/{optionName}");
        map.put("GET-分页查询所有系统设置",requestUrl+"/list/{currentPage}/{pageSize}");
        map.put("POST-保存系统设置",requestUrl+"/save");
        // 返回结果
        return map;
    }
    
    @GetMapping("/list/category/{optionCategory}")
    public Result findOptionsByOptionCategory(@PathVariable String optionCategory) {
    	Result result = new Result();
        try {
        	List<Option> optionList = optionService.findOptionsByOptionCategory(optionCategory);
        	result.setData(optionList);
        	result.setMeta(new Meta(200,"查询系统设置成功"));
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
            PagingData<Option> pagingData = optionService.findAll(currentPage, pageSize);
            result.setMeta(new Meta(200,"查询所有系统设置成功"));
            result.setData(pagingData);

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误，操作失败，请联系管理猿！",e.getMessage()));
        }
        return result;
    }
    
    
    @GetMapping("/one/{optionCategory}/{optionName}")
    public Result findOneByOptionCategoryAndOptionName(@PathVariable String optionCategory,@PathVariable  String optionName){
    	Result result = new Result();
    	try {
    		Option option = optionService.findOneByOptionCategoryAndOptionName(optionCategory,optionName);
    		result.setMeta(new Meta(200,"查询对应系统设置成功"));
    		result.setData(option);
    		
    	}catch (Exception e){
    		e.printStackTrace();
    		result.setMeta(new Meta(500,"服务器内部错误，操作失败，请联系管理猿！",e.getMessage()));
    	}
    	return result;
    }
    
    
    @PostMapping("/save")
    public Result save(@RequestBody Option option){
        Result result = new Result();
        try {
        	optionService.save(option);
            result.setMeta(new Meta(200,"保存系统信息成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误，操作失败，请联系管理猿！",e.getMessage()));
        }
        return result;
    }
}
