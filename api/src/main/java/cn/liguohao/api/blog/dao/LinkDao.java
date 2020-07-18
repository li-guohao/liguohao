package cn.liguohao.api.blog.dao;

import cn.liguohao.api.blog.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: LinkDao
 * @Description: 首页导航栏持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:02:22
 */
public interface LinkDao extends JpaRepository<Link,Long>,JpaSpecificationExecutor<Link> {

    // 根据ID查询链接
    Link findLinkByLid(Long lid);
}
