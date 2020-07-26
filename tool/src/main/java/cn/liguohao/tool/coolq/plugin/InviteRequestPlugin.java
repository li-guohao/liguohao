package cn.liguohao.tool.coolq.plugin;

import org.springframework.stereotype.Component;

import net.lz1998.cq.event.request.CQFriendRequestEvent;
import net.lz1998.cq.event.request.CQGroupRequestEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;

/**
* @ClassName: InviteRequestPlugin
* @Description: 邀请请求处理
* @author liguohao
* @date 2020年7月26日
*
 */
@Component
public class InviteRequestPlugin  extends CQPlugin {

	@Override // 允许QQ847486306邀请机器人进群，并自动同意
	public int onGroupRequest(CoolQ cq, CQGroupRequestEvent event) {
		// 获取 发送者QQ 和 请求
        long userId = event.getUserId();
        if(847486306L == userId) { //允许847486306邀请，自动通过
        	// 同意邀请加群请求
        	cq.setGroupAddRequest(event.getFlag(), event.getSubType(), true, null);
        }else { //不通过
        	cq.setGroupAddRequest(event.getFlag(), event.getSubType(), false, "抱歉，目前只支持QQ847486306邀请机器人进群聊");
        }
		return super.onGroupRequest(cq, event);
	}

	@Override
	public int onFriendRequest(CoolQ cq, CQFriendRequestEvent event) {
		// 同意好友请求
		cq.setFriendAddRequest(event.getFlag(), true, "加小豪机器人好友的人");
		return super.onFriendRequest(cq, event);
	}
	
	
	

}
