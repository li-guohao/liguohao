package cn.liguohao.tool.mirai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.liguohao.tool.mirai.handler.MessageEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;

/**
* @ClassName: MiraiRobot
* @Description: 基于Mirai的机器人启动入口类
* @author liguohao
* @date 2020年8月8日
 */
@Component
public class MiraiRobot{
	
	@Value("${mirai.qq.number}")
	private Long QQNumber;
	
	@Value("${mirai.qq.password}")
	private String QQPassword;
	
	/**
	 * 日志 
	 */
    private final Logger logger = LoggerFactory.getLogger(MiraiRobot.class);
    
    /**
     * 机器人对象
     */
    private Bot bot;

	public MiraiRobot() {
		super();
		init();
		// 设置消息世界监听
		Events.registerEvents(new MessageEventHandler());
		// 阻塞当前线程直到 bot 离线
		//bot.join(); // spring容器中无需此部
	}
	
    /**
    * @Title: init
    * @Description: 初始化登录
     */
	private void init() {
		// 构建
		bot = BotFactoryJvm.newBot(3604880282L, "shrek666", new BotConfiguration() {
            {
                //保存设备信息到文件
                fileBasedDeviceInfo("deviceInfo.json");
                // setLoginSolver();
                // setBotLoggerSupplier();
            }
        });
		
		// 登录
		bot.login();
		
		// 输出好友
		//bot.getFriends().forEach(friend -> logger.info(friend.getId() + ":" + friend.getNick()));
		
        // 获取信息
		logger.info("登录机器人号：" + bot.getId());
		logger.info("登录机器人昵称：" + bot.getNick());
	}

    
    

}
