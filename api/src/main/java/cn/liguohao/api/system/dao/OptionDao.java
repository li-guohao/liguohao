package cn.liguohao.api.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.liguohao.api.system.entity.Option;

/**
 * @ClassName: OptionDao
 * @Description: 设置持久层接口
 * @author: li-guohao
 * @date: 2020-7-18 1:07:37
 */
public interface OptionDao extends JpaRepository<Option,Long>,JpaSpecificationExecutor<Option> {

    // 根据设置名称查询设置信息
    Option findOptionByOptionName(String optionName);

    // 根据分类查询设置
    //@Query(value = "select * from system_option where option_category=?1", nativeQuery = true)
    List<Option> findOptionsByOptionCategory(String optionCategory);

    /**
     * @Title: findAllByPaging
     * @Description: 分页查询所有系统设置
     * @param currentPage
     * @param pageSize
     * @return
     * @return: List<Option>
     */
    @Query(value = "select * from system_option  limit ?1,?2", nativeQuery = true)
	List<Option> findAllByPaging(Integer currentPage, Integer pageSize);



}

