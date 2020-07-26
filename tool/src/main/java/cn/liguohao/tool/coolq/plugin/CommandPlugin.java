package cn.liguohao.tool.coolq.plugin;

import org.springframework.stereotype.Component;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
/**
 * @ClassName: CommandPlugin
 * @Description: QQ机器人所有命令展示插件
 * @author: li-guohao
 * @date: 2020-7-25 19:54:24
 */
@Component
public class CommandPlugin extends CQPlugin {


	@Override
	public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
		// 获取 发送者QQ 和 消息内容
		long groupId = event.getGroupId();
        String msg = event.getMessage();

        if (msg.startsWith("命令")) {
            // 调用API发送hello
        	String result = "命令： 查询所有命令"+
        				"\n"+"BV号：根据BV号查询视频信息" +
        				"\n"+"服务器状态(IP:PORT)：查询Minecraft服务器状态";
        	cq.sendGroupMsg(groupId, result, false);

            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
	}
	
	
}
