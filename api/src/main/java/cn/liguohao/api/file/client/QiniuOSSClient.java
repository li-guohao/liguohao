package cn.liguohao.api.file.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * @ClassName: QiniuOSSClient
 * @Description: 七牛云对象存储客户端
 * @author: li-guohao
 * @date: 2020-7-19 11:14:37
 */
public class QiniuOSSClient {

	
	private String qiniuAccessKey;
	
	private String qiniuSecretKey;
	
	private String qiniuBucket ;
	
	private String qiniuPath ;
	
	private String qiniuRegion;
	
	private String upToken;
	private Region region;
	private UploadManager uploadManager;
	private BucketManager bucketManager;
	
	
	/**
	 * 日志 
	 */
    private final Logger logger = LoggerFactory.getLogger(QiniuOSSClient.class);

	
    /**
     * @Title:QiniuOSSClient
     * @Description:构造方法
     * @param qiniuAccessKey 七牛云权限配置信息
     * @param qiniuSecretKey 七牛云权限配置信息
     * @param qiniuBucket 七牛云对象储存bucket名称
     * @param qiniuPath 七牛云对象储存外网访问路径
     * @param qiniuRegion 七牛云对象储存地域信息
     */
	public QiniuOSSClient(String qiniuAccessKey, String qiniuSecretKey, String qiniuBucket, String qiniuPath,
			String qiniuRegion) {
		super();
		this.qiniuAccessKey = qiniuAccessKey;
		this.qiniuSecretKey = qiniuSecretKey;
		this.qiniuBucket = qiniuBucket;
		this.qiniuPath = qiniuPath;
		this.qiniuRegion = qiniuRegion;
		try {
			// 初始化 region
			if("华东".equals(this.qiniuRegion) || "huadong".equals(this.qiniuRegion)) {
				this.region = Region.huadong();
			} else if("华北".equals(this.qiniuRegion) || "huabei".equals(this.qiniuRegion)) {
				this.region = Region.huabei();
			}else if("华南".equals(this.qiniuRegion) || "huanan".equals(this.qiniuRegion)) {
				this.region = Region.huanan();
			}else if("北美".equals(this.qiniuRegion) || "beimei".equals(this.qiniuRegion)) {
				this.region = Region.beimei();
			}else if("东南亚".equals(this.qiniuRegion) || "xinjiapo".equals(this.qiniuRegion)) {
				this.region = Region.xinjiapo();
			}else {
				throw new RuntimeException("不符合七牛云范围的区域");
			}
			//构造一个带指定 Region 对象的配置类
			Configuration cfg = new Configuration(this.region);
			uploadManager = new UploadManager(cfg);
			// 获取上传凭证
			Auth auth = Auth.create(this.qiniuAccessKey, this.qiniuSecretKey);
			this.upToken = auth.uploadToken(this.qiniuBucket);
			// 构造bucket管理者
			bucketManager = new BucketManager(auth, cfg);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化失败，无法执行上传操作。");
		}
		
	}


	
	/**
	 * @Title: uploadFileByFileName
	 * @Description: 上传文件，指定用户
	 * @param file 上传的文件
	 * @param fileName 文件名称 需要带上后缀名
	 * @return
	 * @throws QiniuException
	 * @throws IOException
	 * @return: String
	 */
	public String uploadFileByFileName(MultipartFile file, String fileName) throws QiniuException, IOException{
		//上传文件
		uploadManager.put(file.getBytes(), fileName, upToken);
		return qiniuPath+"/"+fileName;
	} 
	
	/**
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @param url
	 * @return
	 * @return: boolean
	 * @throws QiniuException 
	 */
	public boolean deleteFile(String fileName) throws QiniuException {
		bucketManager.delete(qiniuBucket, fileName);
		return false;
	}
}
