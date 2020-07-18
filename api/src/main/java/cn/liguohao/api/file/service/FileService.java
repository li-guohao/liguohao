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
     * @param type 文件类型 分为四种 img-图片 video-视频 audio-音频 doc-文档
     * @param currentPage 当前页
     * @param pageSize  每页显示条数
     * @return
     */
    PagingData<File> fingAllByPageingAndType(String type, Integer currentPage, Integer pageSize);

    /**
     * 删除文件 ，并通过FTP删除文件
     * @param fid 数据库文件表ID
     * @param uid 用户ID 登陆FTP时需要用到
     */
    void deleteFileByFid(Long fid,Long uid);

    //根据fid查询文件
    File findOneByFid(Long fid);
}
