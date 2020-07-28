package cn.liguohao.tool.coolq.plugin;

import org.springframework.stereotype.Component;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;

/**
 * @ClassName: PrefixPlugin
 * @Description: 前缀
 * @author: li-guohao
 * @date: 2020-7-25 20:19:47
 */
@Component
public class PrefixPlugin extends CQPlugin {

	private static  String prefix = "/";
	
	@Override
	public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
		String msg = event.getMessage();
		if(msg.startsWith(prefix)) {
			msg = msg.substring(prefix.length());
			event.setMessage(msg);
			return MESSAGE_IGNORE;
		}else {
			return MESSAGE_BLOCK;
		}
	}

	@Override
	public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
		String msg = event.getMessage();
		if(msg.startsWith(prefix)) {
			msg = msg.substring(prefix.length());
			event.setMessage(msg);
			return MESSAGE_IGNORE;
		}else {
			return MESSAGE_BLOCK;
		}
	}
	
	
}
