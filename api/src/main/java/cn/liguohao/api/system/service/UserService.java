package cn.liguohao.api.system.service;

import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.entity.User;

/**
 * @ClassName: UserService
 * @Description: 用户管理服务层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:09:07
 */
public interface UserService {

    // 用户登陆
    public User login(User user);
    // 查询所有用户
    PagingData<User> findAll(Integer currentPage, Integer pageSize);
    // 保存用户
    boolean save(User user);
    // 根据uid获取用户信息
    User findUserByUid(Long uid);
    
    // 根据邮箱获取用户信息
    User findUserByEmail(String email);

}
