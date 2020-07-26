package cn.liguohao.tool.coolq.plugin;

import org.springframework.stereotype.Component;

import cn.liguohao.tool.entity.BiliVideo;
import cn.liguohao.tool.entity.MinecraftServerInfo;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
/**
 * @ClassName: MCServerPlugin
 * @Description: 获取MC服务器信息
 * @author: li-guohao
 * @date: 2020-7-25 14:43:27
 */
@Component
public class MCServerPlugin  extends CQPlugin {

	
	private static String prefix = "服务器";
	
	/**
	 * @Title: onGroupMessage
	 * @Description:  收到私聊消息时会调用这个方法
	 * @param cq 机器人对象，用于调用API，例如发送私聊消息 sendPrivateMsg
	 * @param event 件对象，用于获取消息内容、群号、发送者QQ等
	 * @return 是否继续调用下一个插件，MESSAGE_IGNORE表示继续，MESSAGE_BLOCK表示不继续
	 */
	@SuppressWarnings("unused")
	@Override
	public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
		// 获取 消息内容 群号 发送者QQ
        String msg = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();

        if(msg.startsWith(prefix)) { //是BV号
        	String message = msg.substring(prefix.length());
        	if(message.startsWith("状态")) {
        		message = message.substring("状态".length());
        		MinecraftServerInfo mcServer = null;
        		if(!"".equals(message)) {
        			String[] strArr = message.split(":");
        			Integer port = 25565;
        			if(strArr.length>1) {port = Integer.valueOf(strArr[1]);}
        			mcServer = new MinecraftServerInfo(strArr[0], port);
        		}else {
        			mcServer = new MinecraftServerInfo("mc.liguohao.cn", 10776);
        		}
        		mcServer.fetchData(); //查询数据
        		String result = CQCode.at(userId) + "\n"
        				+"玩家在线状态："+mcServer.getPlayersOnline()+"/"+mcServer.getMaxPlayers()+"\n"
        				+"服务器地址："+mcServer.getAddress()+":"+mcServer.getPort()+"\n"
        				+"服务器状态：："+(mcServer.getPingVersion()==1?"正常开启":"无法连接")+"\n"
        				+"服务器描述："+mcServer.getMotd()+"\n"
        				+"游戏版本：："+mcServer.getGameVersion()+"\n"
        				+"查询者："+event.getSender().getNickname();
        	
        		cq.sendGroupMsg(groupId, result, false);
        	}else {
        		cq.sendGroupMsg(groupId, "未知的查询指令", false);
        	}
        	
        	
        	return MESSAGE_BLOCK;
        }
        
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
	}
	
}
