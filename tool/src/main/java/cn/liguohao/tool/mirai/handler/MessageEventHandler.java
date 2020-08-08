package cn.liguohao.tool.mirai.handler;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.jetbrains.annotations.NotNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.liguohao.tool.common.result.bilibili.Result;
import cn.liguohao.tool.common.util.HttpUtils;
import cn.liguohao.tool.entity.bilibili.Bangumi;
import cn.liguohao.tool.entity.bilibili.Video;
import cn.liguohao.tool.entity.minecraft.ServerInfo;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;

/**
* @ClassName: MainHandler
* @Description: 消息事件处理类
* @author liguohao
* @date 2020年8月8日
 */
public class MessageEventHandler  extends SimpleListenerHost {
	
	//处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
    	exception.printStackTrace();
    	throw new RuntimeException("在事件处理中发生异常", exception);
    }
 
	/**
	* @Title: onGroupMessage
	* @Description: 群聊消息处理
	* @param  参数
	* @return  返回类型
	* @throws
	 */
	@EventHandler
	public ListeningStatus onGroupMessage(GroupMessageEvent event) {
		String msgString = event.getMessage().contentToString();
	    //logger.info(msgString);
	  
		String prefixMCServer = "服务器";
		
		if (msgString.contains("命令")) { // 查询机器人命令
			String result = "命令： 查询所有命令"+
    				"\n"+"BV号或者AV号：查询视频信息" +
    				"\n"+"番剧番剧名：查询番剧信息" +
    				"\n"+"服务器状态IP(域名):PORT：查询Minecraft服务器状态";
		    event.getGroup().sendMessage(result);
		}else if(msgString.startsWith(prefixMCServer)) { //查询Minecraft服务器信息
			String message = msgString.substring(prefixMCServer.length());
			if(message.startsWith("状态")) {
        		message = message.substring("状态".length());
        		ServerInfo mcServer = null;
        		if(!"".equals(message)) {
        			String[] strArr = message.split(":");
        			Integer port = 25565;
        			if(strArr.length>1) {port = Integer.valueOf(strArr[1]);}
        			mcServer = new ServerInfo(strArr[0], port);
        		}else {
        			mcServer = new ServerInfo("mc.liguohao.cn", 10776);
        		}
        		mcServer.fetchData(); //查询数据
        		String result = "玩家在线状态："+mcServer.getPlayersOnline()+"/"+mcServer.getMaxPlayers()+"\n"
        				+"服务器地址："+mcServer.getAddress()+":"+mcServer.getPort()+"\n"
        				+"服务器状态：："+(mcServer.getPingVersion()==1?"正常开启":"无法连接")+"\n"
        				+"服务器描述："+mcServer.getMotd()+"\n"
        				+"游戏版本：："+mcServer.getGameVersion()+"\n"
        				+"查询者："+event.getSender().getNick();
        	
        		 event.getGroup().sendMessage(result);
        	}else {
        		event.getGroup().sendMessage("未知的查询指令");
        	}
			
		}else if(msgString.startsWith("AV") || msgString.startsWith("av") || msgString.startsWith("BV") || msgString.startsWith("bv")) { //BV或者AV视频号
			String httpGetJson = null;
			Video video;
			if(msgString.startsWith("AV") || msgString.startsWith("av")) { //AV号
				httpGetJson = HttpUtils.doGet("http://api.bilibili.com/x/web-interface/view?aid="+msgString.substring("av".length()));
			}else { //BV号
				httpGetJson = HttpUtils.doGet("http://api.bilibili.com/x/web-interface/view?bvid="+msgString);
			}
			// 处理结果
			if(httpGetJson==null) {
				event.getGroup().sendMessage("查询无结果");
			}else {
				// 转化成Java对象
				Result resultObjTemp = JSON.parseObject(httpGetJson,Result.class);
				video = JSONObject.toJavaObject((JSONObject)resultObjTemp.getData(), Video.class);
				// 响应结果
				String result =
        				"AV号："+"av"+video.getAid()+"\n"
        				+"BV号："+video.getBvid()+"\n"
        				+"标题："+video.getTitle()+"\n"
        				+"封面图片URL："+video.getPic() +"\n"
        				+"视频链接URL："+"https://www.bilibili.com/video/av"+video.getAid() +"\n"
        				// 这里注意，api获取的时间戳需要乘以1000才是正确的Java格式时间戳
        				+"上传时间："+ new SimpleDateFormat("yyyy年MM月dd日").format(video.getPubdate()*1000)+"\n"
        				+"查询者："+event.getSender().getNick()+"("+event.getSender().getId()+")"+"\n"
        				+"\n"+"视频简介：\n"+video.getDesc()
        				+"\n"+"视频封面：\n"+video.getDesc();
				// 获取封面图片
				final Image image ;
				try {
					URL pathUrl = new URL(video.getPic());
					DataInputStream dataInputStream;
					dataInputStream = new DataInputStream(pathUrl.openStream());
					image = event.getGroup().uploadImage(dataInputStream);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("获取封面图片失败");
				}
				
	        	// 发送消息  
				event.getGroup().sendMessage(
							MessageUtils.newImage(image.getImageId()) // 演示图片, 可能已过期
	                        .plus("\n")
							.plus(new At(event.getSender())) //At 消息发送者
							.plus("\n")
							.plus(result) // 消息
						);
			}
			
			
		}else if(msgString.startsWith("番剧")) {
			msgString = msgString.substring("番剧".length());
			String result = HttpUtils.doGet("http://api.bilibili.com/x/web-interface/search/type?search_type=media_bangumi&order=click&keyword="+msgString);
			Result resultObj = JSON.parseObject(result,Result.class);
			JSONObject JsonObj = (JSONObject)resultObj.getData();
			JSONArray resJsonArr = (JSONArray) JsonObj.get("result");
			Bangumi bangumi = JSONObject.toJavaObject(resJsonArr.getJSONObject(0), Bangumi.class);
			// 去掉HTML标签
			String title = bangumi.getTitle();
			title = title.replaceAll("</?[^>]+>", "");
			bangumi.setTitle(title);
			
			try {
	        	String response = "标题："+bangumi.getTitle() + "\n"
	        				+"地区："+bangumi.getAreas() + "\n"
	        				+"类型："+bangumi.getStyles() + "\n"
	        				+"地址："+bangumi.getGoto_url() + "\n"
	        				+"封面URL："+"http:"+bangumi.getCover() + "\n"
	        				+"\n"+"CV：\n"+bangumi.getCv() + "\n"
	        				+"\n"+"STAFF：\n"+bangumi.getStaff() + "\n"
	        				+"\n"+"介绍：\n"+ bangumi.getDesc();
	        	
	        	// 获取封面图片
				final Image image ;
				try {
					URL pathUrl = new URL("http:"+bangumi.getCover());
					DataInputStream dataInputStream;
					dataInputStream = new DataInputStream(pathUrl.openStream());
					image = event.getGroup().uploadImage(dataInputStream);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("获取封面图片失败");
				}
				
	        	// 发送消息  
				event.getGroup().sendMessage(
							MessageUtils.newImage(image.getImageId()) // 演示图片, 可能已过期
							.plus("\n") //换行
	                        .plus(new At(event.getSender())) //At 消息发送者
	                        .plus("\n") //换行
							.plus(response) // 消息
						);
        	} catch (Exception e) {
        		event.getGroup().sendMessage(new At(event.getSender()) + "\n" + "查无此番剧");
			}
			
			
		}
		
		//保持监听
	    return ListeningStatus.LISTENING;
	}

      
	/**
	* @Title: onMessage
	* @Description: 私聊消息处理
	* @param  参数
	* @return  返回类型
	* @throws
	 */
	@NotNull
	@EventHandler
	public ListeningStatus onMessage(@NotNull MessageEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理
//		String msg = event.getMessage().contentToString();
//		if(msg.contains("hello")) {
//			event.getSubject().sendMessage("hello "+event.getSender().getNick());
//		}
		return ListeningStatus.LISTENING; // 表示继续监听事件
      // return ListeningStatus.STOPPED; // 表示停止监听事件
	}
      
}
