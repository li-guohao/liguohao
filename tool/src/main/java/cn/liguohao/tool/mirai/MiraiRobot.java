package cn.liguohao.tool.mirai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		// 构建 本地测试用3604880282 线上请改成 2891055788
		bot = BotFactoryJvm.newBot(2891055788L, "shrek666", new BotConfiguration() {
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
