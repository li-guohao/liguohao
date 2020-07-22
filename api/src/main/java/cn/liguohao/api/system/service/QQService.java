package cn.liguohao.api.system.service;

import cn.liguohao.api.system.entity.QQ;

/**
 * @ClassName: QQService
 * @Description: qq第三方登陆
 * @author: li-guohao
 * @date: 2020-7-21 22:33:38
 */
public interface QQService {
	
	QQ findQQByOpenID(String openID);
	
	void save(QQ qq);
}
