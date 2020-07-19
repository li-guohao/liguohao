package cn.liguohao.api.file.service;

import cn.liguohao.api.file.entity.File;
import cn.liguohao.api.response.PagingData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: FileService
 * @Description: 文件服务层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:06:09
 */
public interface FileService {

    // 上传文件
    File uploadFile(MultipartFile file, Long uid,String requestUri) throws IOException;

    /**
     * 查询所有文件
     * @param currentPage 当前页
     * @param pageSize  每页显示条数
     * @return
     */
    PagingData<File> fingAllByPageingAndType( Integer currentPage, Integer pageSize);

    /**
     * 删除文件 ，并通过FTP删除文件
     * @param fid 数据库文件表ID
     */
    void deleteFileByFid(Long fid);

    //根据fid查询文件
    File findOneByFid(Long fid);
}
