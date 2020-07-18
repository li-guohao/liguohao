package cn.liguohao.api.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.liguohao.api.response.PagingData;
import cn.liguohao.api.system.dao.OptionDao;
import cn.liguohao.api.system.entity.Option;
import cn.liguohao.api.system.service.OptionService;

/**
 * @ClassName: OptionServiceImpl
 * @Description: 设置接口实现类
 * @author: li-guohao
 * @date: 2020-7-18 1:08:37
 */
@Service
public class OptionServiceImpl implements OptionService{

    @Autowired
    private OptionDao optionDao;


    @Override
    public List<Option> findOptionsByOptionCategory(String optionCategory) {
        return optionDao.findOptionsByOptionCategory(optionCategory);
    }


	@Override
	public PagingData<Option> findAll(Integer currentPage, Integer pageSize) {
		//查询结果
        PagingData<Option> pagingData = new PagingData<Option>(currentPage,pageSize);

        //limit a,b a是从0开始，所以下方需要减一
        List<Option> optionList = optionDao.findAllByPaging(currentPage-1, pageSize);

        // 设置总数
        Long count = optionDao.count();
        pagingData.setTotal(count.intValue());
        //Pageable pageable = new PageRequest(currentPage,pageSize);

        pagingData.setDataArray(optionList);
        //返回结果
        return pagingData;
	}


	@Override
	public void save(Option option) {
		optionDao.save(option);
	}
}
