package cn.liguohao.api.system.service;

import java.util.List;

import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.entity.Option;

/**
 * @ClassName: OptionService
 * @Description: 设置服务接口
 * @author: li-guohao
 * @date: 2020-7-18 1:08:58
 */
public interface OptionService {

    /**
     * @Title: findOptionsByOptionCategory
     * @Description: 根据分类查询所有设置
     * @param optionCategory 设置分类
     * @return
     * @return: List<Option> 返回结果
     */
    List<Option> findOptionsByOptionCategory(String optionCategory);
    
    /**
     * @Title: findAll
     * @Description: 查询所有系统设置
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return
     * @return: PagingData<Option>
     */
    PagingData<Option> findAll(Integer currentPage, Integer pageSize);
    
    /**
     * @Title: save
     * @Description: 保存系统设置
     * @param option
     */
    void save(Option option);
    
    /**
     * @Title: findOneByOptionCategoryAndOptionName
     * @Description: 根据设置的分类和名称查询系统设置
     * @param optionCategory
     * @param optionName
     * @return
     * @return: Option
     */
	Option findOneByOptionCategoryAndOptionName(String optionCategory, String optionName);
}
