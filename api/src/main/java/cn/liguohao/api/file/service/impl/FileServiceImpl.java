package cn.liguohao.api.file.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.liguohao.api.file.client.QiniuOSSClient;
import cn.liguohao.api.file.dao.FileDao;
import cn.liguohao.api.file.entity.File;
import cn.liguohao.api.file.service.FileService;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.entity.Option;
import cn.liguohao.api.system.entity.User;
import cn.liguohao.api.system.service.OptionService;
import cn.liguohao.api.system.service.UserService;
import cn.liguohao.api.utils.UUIDUtils;

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
    @Autowired
    private OptionService optionService;
    
    /**
             *  日志
     */
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    



    @Override
    public File uploadFile(MultipartFile file, Long uid,String requestUri) throws IOException {
        // 获取用户信息
        User user =  userService.findUserByUid(uid);
        // 新建数据库文件对象
        File dbfile = new File();
        if(dbfile.getDescription()==null || "".equals(dbfile.getDescription())) dbfile.setDescription("这是默认的文件描述");

        // 获取文件的后缀格式
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        
        
	    //生成文件名 日期加UUID
		Calendar now = Calendar.getInstance();
		String fileName = now.get(Calendar.YEAR) +"_" + (now.get(Calendar.MONTH) + 1) + "_"
	              + now.get(Calendar.DAY_OF_MONTH) + "_" 
	              + UUIDUtils.getId() + "." + suffix;
		
        try {
        	// 初始化客户端
        	// 从数据库获取七牛云对象存储信息
        	List<Option> optionList = optionService.findOptionsByOptionCategory("qiniuoss");
        	
        	String qiniuAccessKey = "";
        	String qiniuSecretKey = "";
        	String qiniuBucket = "";
        	String qiniuPath = "";
        	String qiniuRegion = "";
        	for (Option option : optionList) {
				switch (option.getOptionName()) {
				case "qiniuAccessKey":
					qiniuAccessKey = option.getOptionValue();
					break;
				case "qiniuSecretKey":
					qiniuSecretKey = option.getOptionValue();
					break;
				case "qiniuPath":
					qiniuPath = option.getOptionValue();
					break;
				case "qiniuBucket":
					qiniuBucket = option.getOptionValue();
					break;
				case "qiniuRegion":
					qiniuRegion = option.getOptionValue();
					break;
				default:
					// 未配置时直接抛出异常
		        	throw new RuntimeException("未配置七牛云，上传文件失败，请到后台配置！");
				}
			}
        	
        	// 运用客户端上传文件
        	QiniuOSSClient qiniuOSSClient = new QiniuOSSClient(qiniuAccessKey, qiniuSecretKey, qiniuBucket, qiniuPath, qiniuRegion);
        	String url = qiniuOSSClient.uploadFileByFileName(file, fileName);
            
        	// 将文件信息保存至数据库
            dbfile.setName(fileName);
            dbfile.setUploadTime(new Date());
            dbfile.setUpper(user.getEmail());
            dbfile.setUrl(url);
            dbfile.setType(suffix);
            fileDao.save(dbfile);
        } catch (IOException e) {
            logger.error("上传文件失败");
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        } catch(Exception e) {
        	logger.error("上传文件失败");
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
        return dbfile;
    }

    @Override
    public PagingData<File> fingAllByPageingAndType(Integer currentPage, Integer pageSize) {
        PagingData<File> pagingData = new PagingData<File>(currentPage,pageSize);

        // 查询
        List<File> fileList = fileDao.fingAllByPageingAndType((currentPage-1)*pageSize , pageSize);
        // 设置数据
        pagingData.setDataArray(fileList);
        // 设置总数
        Long count = fileDao.count();
        pagingData.setTotal(count.intValue());
        return pagingData;
    }

    @Override
    @Transactional
    public void deleteFileByFid(Long fid) {
        // 获取文件信息
        File file = fileDao.findFileByFid(fid);
        // 删除数据库中此文件信息
        fileDao.delete(file);

        // 删除文件
        try {
        	// 初始化客户端
        	// 从数据库获取七牛云对象存储信息
        	List<Option> optionList = optionService.findOptionsByOptionCategory("qiniuoss");
        	String qiniuAccessKey = "";
        	String qiniuSecretKey = "";
        	String qiniuBucket = "";
        	String qiniuPath = "";
        	String qiniuRegion = "";
        	for (Option option : optionList) {
				switch (option.getOptionName()) {
				case "qiniuAccessKey":
					qiniuAccessKey = option.getOptionValue();
					break;
				case "qiniuSecretKey":
					qiniuSecretKey = option.getOptionValue();
					break;
				case "qiniuPath":
					qiniuPath = option.getOptionValue();
					break;
				case "qiniuBucket":
					qiniuBucket = option.getOptionValue();
					break;
				case "qiniuRegion":
					qiniuRegion = option.getOptionValue();
					break;
				default:
					break;
				}
			}
        	
        	// 运用客户端删除文件
        	QiniuOSSClient qiniuOSSClient = new QiniuOSSClient(qiniuAccessKey, qiniuSecretKey, qiniuBucket, qiniuPath, qiniuRegion);
            qiniuOSSClient.deleteFile(file.getName());
        } catch (IOException e) {
            logger.error("上传文件失败");
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public File findOneByFid(Long fid) {
        return fileDao.findFileByFid(fid);
    }
}
