package cn.liguohao.api.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.liguohao.api.system.dao.QQDao;
import cn.liguohao.api.system.entity.QQ;
import cn.liguohao.api.system.service.QQService;
/**
 * @ClassName: QQServiceImpl
 * @Description: QQ第三方登陆接口实现类
 * @author: li-guohao
 * @date: 2020-7-21 22:34:43
 */
@Service
public class QQServiceImpl implements QQService {

	@Autowired
	private QQDao qqdao;
	
	@Override
	public QQ findQQByOpenID(String openID) {
		return qqdao.findQQByOpenID(openID);
	}

	@Override
	public void save(QQ qq) {
		qqdao.save(qq);
	}

}
