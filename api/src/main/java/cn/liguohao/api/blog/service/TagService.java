package cn.liguohao.api.blog.service;

import cn.liguohao.api.blog.entity.Tag;
import cn.liguohao.api.response.PagingData;

/**
 * @ClassName: TagService
 * @Description: 标签服务层
 * @author: li-guohao
 * @date: 2020-7-18 1:04:36
 */
public interface TagService {

    // 添加
    void save(Tag tag);

    // 分页查询所有
    PagingData<Tag> findAllByPaging(Integer currentPage, Integer pageSize);

    // 根据ID查询
    Tag findTagByAid(Long tid);

    //  更新文章
    void mergeTag(Tag tag);

    // 根据ID 删除
    void deleteTagByAid(Long tid);

    // 删除未被引用标签，即是 被引用数=0
    void deleteByNotUse();
}
