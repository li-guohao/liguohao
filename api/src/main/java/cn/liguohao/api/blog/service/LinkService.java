package cn.liguohao.api.blog.service;

import cn.liguohao.api.blog.entity.Link;

import java.util.List;

/**
 * @ClassName: LinkService
 * @Description: 头部导航栏接口
 * @author: li-guohao
 * @date: 2020-7-18 1:04:28
 */
public interface LinkService {

    // 查询所有链接
    List<Link> findAll();

    // 保存链接
    Link save(Link link);

    // 根据ID查询链接
    Link findOneByLid(Long lid);

    // 根据ID删除链接
    void deleteOneByLid(Long lid);
}
