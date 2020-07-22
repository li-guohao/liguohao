package cn.liguohao.api.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.dao.UserDao;
import cn.liguohao.api.system.entity.User;
import cn.liguohao.api.system.service.UserService;
import cn.liguohao.api.utils.BeanUtil;
import cn.liguohao.api.utils.UUIDUtils;

/**
 * @ClassName UserServiceImpl
 * @Author li-guohao
 * @Date 2020/3/6 17:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        // 判断是否依据注册
        User userEmailExist = userDao.findUserByEmail(user.getEmail());
        if(userEmailExist==null ){ //未注册
            user.setUsername("无名者");
            user.setRegisterTime(new Date());
            userDao.save(user);
        }else { //已注册
            user = userDao.findUserByEmailAndPassword(user.getEmail(),user.getPassword());
        }
        return user;
    }

    @Override
    public PagingData<User> findAll(Integer currentPage, Integer pageSize) {
        //查询结果
        PagingData<User> pagingData = new PagingData<User>(currentPage,pageSize);

        //limit a,b a是从0开始，所以下方需要减一
        List<User> userList = userDao.findAllByPaging(currentPage-1, pageSize);

        // 设置总数
        Long count = userDao.count();
        pagingData.setTotal(count.intValue());
        //Pageable pageable = new PageRequest(currentPage,pageSize);

        pagingData.setDataArray(userList);
        //返回结果
        return pagingData;
    }

    @Override
    public boolean save(User user) {
        if(user.getUid()==null){ //新增用户
            user.setRegisterTime(new Date());
            user.setStatus(1);
            user.setToken(UUIDUtils.getId());
            userDao.save(user);
        }else {                  //更新用户
            User userRes = userDao.findUserByUid(user.getUid());
            BeanUtil.copyFieldByIsExist(user,userRes);
            userDao.save(userRes);
        }
        return true;
    }

    @Override
    public User findUserByUid(Long uid) {
        return userDao.findUserByUid(uid);
    }

	@Override
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}
}
