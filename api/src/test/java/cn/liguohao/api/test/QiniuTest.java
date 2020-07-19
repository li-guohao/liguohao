package cn.liguohao.api.test;

import java.io.File;
import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * @ClassName: QiniuTest
 * @Description: 七牛云测试
 * @author: li-guohao
 * @date: 2020-7-19 10:30:05
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class QiniuTest {
	
	
	@Value("${qiniu.accessKey}")
	private String qiniuAccessKey;
	
	@Value("${qiniu.secretKey}")
	private String qiniuSecretKey;
	
	@Value("${qiniu.bucket}")
	private String qiniuBucket;
	
	@Value("${qiniu.path}")
	private String qiniuPath;
	
	/**
	 * 日志 
	 */
    private final Logger logger = LoggerFactory.getLogger(QiniuTest.class);

	@Test
	public void uploadFile() {
		
		//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		String localFilePath = "W:\\liguohao.cn\\liguohao\\api\\src\\main\\resources\\static\\test\\test.jpg";
		File file = new File(localFilePath);
		
		//生成文件名 日期加UUID
		Calendar now = Calendar.getInstance();
		String fileName = now.get(Calendar.YEAR) +"_" + (now.get(Calendar.MONTH) + 1) + "_"
                + now.get(Calendar.DAY_OF_MONTH) + "_" + file.getName();
		
		
		// 获取凭证
		Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("callbackUrl", qiniuPath+fileName);
		putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		putPolicy.put("callbackBodyType", "application/json");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(qiniuBucket, null, expireSeconds, putPolicy);
		logger.info("上传凭证："+upToken);
		
		
		
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		//String key = null;
		
		//构造配置对象
		//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.huadong());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		
		//上传文件
		try {
		    Response response = uploadManager.put(localFilePath, fileName, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    logger.info(putRet.key);
		    logger.info(putRet.hash);
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}
		
	}
	
	
	
}
