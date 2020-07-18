package cn.liguohao.api.file.service.impl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.liguohao.api.file.dao.FileDao;
import cn.liguohao.api.file.entity.File;
import cn.liguohao.api.file.service.FileService;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.service.UserService;

/**
 * @ClassName: FileServiceImpl
 * @Description: 文件服务层接口实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:06:27
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private UserService userService;
    @Autowired
    private FileDao fileDao;
    //@Autowired
    //private QiuyunOSSClient qiuyunOSSClient;

    // 文件类型 根据 类型进行分类
    private  List<String>  imgType = Lists.newArrayList("jpg","jpeg", "png", "bmp", "gif");
    //private  List<String> videoType = Lists.newArrayList("avi","mp4");
    //private  List<String> audioType = Lists.newArrayList("mp3");
    //private  List<String> docType = Lists.newArrayList("doc","docx","md","text","html");

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);




    @Override
    public File uploadFile(MultipartFile file, Long uid,String requestUri) throws IOException {
        // 获取用户信息
//        User user = 
        		userService.findUserByUid(uid);
        // 新建数据库文件对象
        File dbfile = new File();
        if(dbfile.getDescription()==null || "".equals(dbfile.getDescription())) dbfile.setDescription("这是默认的文件描述");

        // 获取文件名，带后缀
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀格式
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        // 首先校验图片格式
        if (imgType.contains(fileSuffix)) {
//            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
//            String newFileName = UUIDUtils.getId() + originalFilename;
//            // 按照年月日分三层文件夹存储文件
//            Calendar now = Calendar.getInstance();
//            // 生成服务器上的相对路径
//            String relativePath = now.get(Calendar.YEAR) +"/" + (now.get(Calendar.MONTH) + 1) + "/"
//                + now.get(Calendar.DAY_OF_MONTH) + "/" ;
//            // 上传文件

        } else {
            // 非法文件
            logger.error("此图片非法的格式，无法上传");
            throw new RuntimeException("此图片非法的格式，无法上传");
        }
        return dbfile;
    }

    @Override
    public PagingData<File> fingAllByPageingAndType(String type, Integer currentPage, Integer pageSize) {
        PagingData<File> pagingData = new PagingData<File>(currentPage,pageSize);

        // 查询
        List<File> fileList = fileDao.fingAllByPageingAndType(type,(currentPage-1)*pageSize , pageSize);
        // 设置数据
        pagingData.setDataArray(fileList);
        // 设置总数
        Long count = fileDao.count();
        pagingData.setTotal(count.intValue());
        return pagingData;
    }

    @Override
    @Transactional
    public void deleteFileByFid(Long fid, Long uid) {
        // 获取文件信息
        File file = fileDao.findFileByFid(fid);
        // 删除数据库中此文件信息
        fileDao.delete(file);
        String url = file.getUrl();
        // 相对路径
        String relativePath = url.substring(url.indexOf("20"),url.lastIndexOf("/"))  +"/";
        logger.info("文件在用户根目录下的相对路径为："+relativePath);
        // 文件名称
        String fileName = url.substring(url.lastIndexOf("/") +1);
        logger.info("文件在服务器目录下的名称为："+fileName);

        // 调用FTP删除文件

        // 删除文件
        //boolean isSuccess = ftpCilent.deleteFile(relativePath,fileName);
        //if(!isSuccess) throw  new RuntimeException("文件删除失败，FTP操作失败");
    }

    @Override
    public File findOneByFid(Long fid) {
        return fileDao.findFileByFid(fid);
    }
}
