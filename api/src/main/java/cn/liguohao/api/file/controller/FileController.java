package cn.liguohao.api.file.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.liguohao.api.file.entity.File;
import cn.liguohao.api.file.service.FileService;
import cn.liguohao.api.response.Meta;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.response.Result;


/**
 * @ClassName: FileController
 * @Description: 文件Controller
 * @author: li-guohao
 * @date: 2020-7-18 1:05:28
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    /**
     * 环境，是生产环境还是开发环境
     */
    @Value("${MODE}")
    private String MODE;


    @GetMapping("")
    public Map<String,Object> index(HttpServletRequest request){
        // 获取请求路径
        Object requestUrl = request.getAttribute("requestUrl");
        // 新建对象
        HashMap<String, Object> map = new HashMap<>();
        // 构建数据
        if("production".equals(MODE)){ //线上(生产)环境
            requestUrl += "file";
        }
        map.put("POST-文件上传",requestUrl+"/upload");
        map.put("GET-分页查询所有文件",requestUrl+"/list/{type}/{currentPage}/{pageSize}");
        map.put("DELETE-根据ID删除文件",requestUrl+"/{fid}");
        map.put("GET-根据fid查询文件",requestUrl+"/{fid}");
        // 返回结果
        return map;
    }

    // 文件上传
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file , HttpServletRequest request){
        Long uid = Long.valueOf(request.getHeader("UID"));
        Result result = new Result();
        // 获取请求URI
        String requestUri = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        try {
            File fileRes = fileService.uploadFile(file,uid,requestUri);
            result.setData(fileRes);
            result.setMeta(new Meta(200,"上传文件成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误，上传文件失败，或许是您还未配置对象存储信息",e.getMessage()));
        }
        return result;
    }

    // 查询文件 根据类型
    @GetMapping("/list/{currentPage}/{pageSize}")
    public Result findAll(HttpServletRequest request,
                          @PathVariable Integer currentPage,
                          @PathVariable  Integer pageSize) {
        Result result = new Result();
        try {
        	PagingData<File> pagingData = fileService.fingAllByPageingAndType(currentPage, pageSize);
            result.setMeta(new Meta(200,"查询所有文件成功"));
            result.setData(pagingData);

        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误",e.getMessage()));
        }
        return result;
    }

    // 根据fid删除文件，同时通过FTP从服务器删除文件
    @DeleteMapping("/{fid}")
    public Result deleteOneByFid(@PathVariable Long fid){
        Result result = new Result();
        try {
            fileService.deleteFileByFid(fid);
            result.setMeta(new Meta(200,"删除文件成功"));
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误，操作失败",e.getMessage()));
        }
        return result;
    }

    // 根据fid查询文件
    @GetMapping("/{fid}")
    public Result findOneByFid(@PathVariable Long fid){
        Result result = new Result();
        try {
            File file =  fileService.findOneByFid(fid);
            if(file==null) {
                result.setMeta(new Meta(404,"文件不存在"));
            }else {
                result.setMeta(new Meta(200,"查询文件成功"));
            }
            result.setData(file);
        }catch (Exception e){
            e.printStackTrace();
            result.setMeta(new Meta(500,"服务器内部错误，操作失败",e.getMessage()));
        }
        return result;
    }


}
