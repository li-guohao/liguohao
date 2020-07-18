package cn.liguohao.api.blog.service.impl;

import cn.liguohao.api.blog.dao.LinkDao;
import cn.liguohao.api.blog.entity.Link;
import cn.liguohao.api.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: LinkServiceImpl
 * @Description: 导航栏服务层接口实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:03:50
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public List<Link> findAll() {
        return linkDao.findAll();
    }

    @Override
    public Link save(Link link) {
        // 父ID不存在时设置为-1
        if(link.getPartentLid()==null || "".equals(link.getPartentLid().toString())) link.setPartentLid(-1L);
        return linkDao.save(link);
    }

    @Override
    public Link findOneByLid(Long lid) {
        return linkDao.findLinkByLid(lid);
    }

    @Override
    public void deleteOneByLid(Long lid) {
        linkDao.delete(linkDao.getOne(lid));
    }
}
