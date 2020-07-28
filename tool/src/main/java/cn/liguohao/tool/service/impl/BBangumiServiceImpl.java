package cn.liguohao.tool.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.liguohao.tool.common.result.bilibili.Result;
import cn.liguohao.tool.common.util.HttpUtils;
import cn.liguohao.tool.entity.bilibili.Bangumi;
import cn.liguohao.tool.service.BBangumiService;

/**
* @ClassName: BBangumiServiceImpl
* @Description: 哔哩哔哩番剧服务层实现类
* @author liguohao
* @date 2020年7月28日
*
 */
@Service
public class BBangumiServiceImpl implements BBangumiService {

//	@Autowired
//	private BBangummiDao bangumiDao;
	@Value("${BiliBiliSearchBangumiApi}")
	private String BiliBiliSearchBangumiApi;
	
	@Override
	public Bangumi findBangmiByKeyword(String keyword) {
		String result = HttpUtils.doGet(BiliBiliSearchBangumiApi+"?search_type=media_bangumi&order=click&keyword="+keyword);
		Result resultObj = JSON.parseObject(result,Result.class);
		JSONObject JsonObj = (JSONObject)resultObj.getData();
		JSONArray resJsonArr = (JSONArray) JsonObj.get("result");
		Bangumi bangumi = JSONObject.toJavaObject(resJsonArr.getJSONObject(0), Bangumi.class);
		// 去掉HTML标签
		String title = bangumi.getTitle();
		title = title.replaceAll("</?[^>]+>", "");
		bangumi.setTitle(title);
		return bangumi;
	}

}
