package cn.liguohao.api.system.dao;

import cn.liguohao.api.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: UserDao
 * @Description: 用户持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:07:57
 */
public interface UserDao extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {

    // 根据用户名密码查询用户
    User findUserByUsernameAndPassword(String username, String password);

    // 根据邮箱和密码查询用户
    User findUserByEmailAndPassword(String email,String password);

    // 根据邮箱查询用户
    User findUserByEmail(String email);


    // 根据uid查询用户
    User findUserByUid(Long uid);

    // 分页查询
    @Query(value = "select * from system_user where status <> 0 limit ?1,?2", nativeQuery = true)
    List<User> findAllByPaging(Integer currentPage, Integer pageSize);


}

