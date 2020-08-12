package cn.liguoaho.rebot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
* @ClassName: Server
* @Description: 机器人入口类
* @author liguohao
* @date 2020年8月9日
 */
public class Server {
	
	private static Logger log = Logger.getLogger(Server.class);
	private static  InputStream in = Server.class.getResourceAsStream("/config.properties");
	private static Properties props = new Properties();
	private static InputStreamReader inputStreamReader;
	
	static {
		try {
			inputStreamReader = new InputStreamReader(in, "UTF-8");
			props.load(inputStreamReader);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	* @Title: main
	* @Description: 入口方法
	 */
	public static void main(String[] args) {
		log.info("启动应用中···");
		log.info("开始开启机器人");
		log.debug(props.get("qq.number"));
		log.debug(props.get("qq.password")!=null?"*********":"密码未输入");
		startRoboot();
	}
	
	
	
	
	/**
	* @Title: startRoboot
	* @Description: 启动机器人
	* @return  启动机器人是否成功
	 */
	private static boolean startRoboot() {
		Object qqNumber = props.get("qq.number");
		if(qqNumber==null || "".equals(qqNumber)) throw new RuntimeException("主要信息未配置，请在config.properties文件中配置好对应信息");
		// 获取QQ号码
		Long number = Long.valueOf(qqNumber!=null || "".equals(qqNumber)?qqNumber.toString():"-1");
		
		return true;
	}
}
