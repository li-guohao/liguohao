package cn.liguohao.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.liguohao.api.system.entity.QQ;

/**
 * @ClassName: QQDao
 * @Description: 第三方QQ登陆信息持久层接口
 * @author: li-guohao
 * @date: 2020-7-21 22:27:18
 */
public interface QQDao extends JpaRepository<QQ,Long>,JpaSpecificationExecutor<QQ> {

	/**
	* @Title: findQQByOpenID
	* @Description: 根据OpenID查询
	* @param openID 用户唯一标识符合
	* @return
	* @return: QQ
	*/
	QQ findQQByOpenID(String openID);

}

