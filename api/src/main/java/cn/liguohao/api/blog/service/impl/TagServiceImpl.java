package cn.liguohao.api.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.liguohao.api.blog.dao.TagDao;
import cn.liguohao.api.blog.entity.Tag;
import cn.liguohao.api.blog.service.TagService;
import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.utils.BeanUtil;

/**
 * @ClassName: TagServiceImpl
 * @Description: 标签服务层实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:03:59
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public void save(Tag tag) {
        tag.setCreateTime(new Date());
        tag.setStatus(1);
        tag.setReferenceCount(1); //标签创建时被引用一次
        tagDao.save(tag);
    }

    @Override
    public PagingData<Tag> findAllByPaging(Integer currentPage, Integer pageSize) {
        PagingData<Tag> pagingData = new PagingData<Tag>(currentPage,pageSize);

        // 查询
        List<Tag> tagList = tagDao.findAllByPagingAndName((currentPage-1)*pageSize , pageSize);
        // 设置数据
        pagingData.setDataArray(tagList);
        // 设置总数
        Long count = tagDao.findCount();
        pagingData.setTotal(count.intValue());
        return pagingData;
    }

    @Override
    public Tag findTagByAid(Long tid) {
        return tagDao.findTagByTid(tid);
    }

    @Override
    public void mergeTag(Tag tag) {
        // 根据ID查询
        Tag tagRes= findTagByAid(tag.getTid());
        // 更新
        BeanUtil.copyFieldByIsExist(tag,tagRes);
        // 保存更改
        tagDao.save(tagRes);
    }

    @Override
    public void deleteTagByAid(Long tid) {
        // 根据ID查询
        Tag tagRes= findTagByAid(tid);
        tagRes.setStatus(0);
        // 保存更改
        tagDao.save(tagRes);
    }

    @Override
    public void deleteByNotUse() {
        // 查询所有标签
        List<Tag> list = tagDao.findAll();
        for(Tag tag : list){
            Integer referenceCount = tag.getReferenceCount();
            if(referenceCount==0){ // 标签被引用数为0
                tag.setStatus(0); //设置状态为已删除
                // 保存更改
                tagDao.save(tag);
            }
        }
    }
}
