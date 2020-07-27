package cn.liguohao.tool.coolq.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.liguohao.tool.entity.bilibili.Video;
import cn.liguohao.tool.service.BVideoService;
import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;

/**
 * @ClassName: BVPlugin
 * @Description: 根据BV号查询视频信息
 * @author: li-guohao
 * @date: 2020-7-25 13:52:44
 */
@Component
public class BVPlugin  extends CQPlugin  {
	
	@Autowired
	private BVideoService bService;
	
	
	
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

        if(msg.startsWith("AV") || msg.startsWith("av") || msg.startsWith("BV") || msg.startsWith("bv")) { //是BV号
        	//cq.sendGroupMsg(groupId, "查询中... 可能需要一会儿，请稍后。", false);
        	Video bv = bService.getVideoByNumber(msg);
        	String result = CQCode.at(userId) + "\n"
        				+"AV号："+"av"+bv.getAid()+"\n"
        				+"BV号："+bv.getBvid()+"\n"
        				+"标题："+bv.getTitle()+"\n"
        				+"封面图片URL："+bv.getPic() +"\n"
        				+"上传时间："+bv.getPubdate()+"\n"
        				+"视频总计持续时长："+bv.getDuration()+"秒"+"\n"
        				+"查询次数："+bv.getSearchCount()+"\n"
        				+"查询者："+event.getSender().getNickname()+"("+userId+")"+"\n"
        				+"视频简介：\n"+bv.getDesc();
        	
        	cq.sendGroupMsg(groupId, result, false);
        	return MESSAGE_BLOCK;
        }
        
        // 继续执行下一个插件
        return MESSAGE_IGNORE;
	}
	
}
