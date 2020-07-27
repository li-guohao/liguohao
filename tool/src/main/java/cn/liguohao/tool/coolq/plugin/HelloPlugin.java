package cn.liguohao.tool.coolq.plugin;

import org.springframework.stereotype.Component;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.event.notice.CQGroupIncreaseNoticeEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;


/**
 * @ClassName: Hello
 * @Description: 问候插件
 * @author: li-guohao
 * @date: 2020-7-25 12:27:06
 */
@Component
public class HelloPlugin  extends CQPlugin {

	/**
	 * @Title: onPrivateMessage
	 * @Description:  收到私聊消息时会调用这个方法
	 * @param cq 机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
	 * @param event 件对象，用于获取消息内容、群号、发送者QQ等
	 * @return 是否继续调用下一个插件，MESSAGE_IGNORE表示继续，MESSAGE_BLOCK表示不继续
	 */
	@Override
	public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
		// 获取 发送者QQ 和 消息内容
        long userId = event.getUserId();
        String msg = event.getMessage();

        if (msg.equals("hi")) {
            // 调用API发送hello
            cq.sendPrivateMsg(userId, "hello"+event.getSender().getNickname(), false);

            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
	}

	/**
	 * @Title: onGroupMessage
	 * @Description:  收到私聊消息时会调用这个方法
	 * @param cq 机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
	 * @param event 件对象，用于获取消息内容、群号、发送者QQ等
	 * @return 是否继续调用下一个插件，MESSAGE_IGNORE表示继续，MESSAGE_BLOCK表示不继续
	 */
	@Override
	public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
		// 获取 消息内容 群号 发送者QQ
        String msg = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();

        if (msg.equals("hi")) {
            // 回复内容为 at发送者 + hi
            String result = CQCode.at(userId) + "hello";
            // 调用API发送消息
            cq.sendGroupMsg(groupId, result, false);
            // 不执行下一个插件
            return MESSAGE_BLOCK;
        }

        // 继续执行下一个插件
        return MESSAGE_IGNORE;
	}

	@Override
	public int onGroupIncreaseNotice(CoolQ cq, CQGroupIncreaseNoticeEvent event) {
		 cq.sendGroupMsg(event.getGroupId(), CQCode.at(event.getUserId())+" 欢迎您的加入！这里是小豪机器人。", false);
		return MESSAGE_IGNORE;
	}
	
	
	
	
}
