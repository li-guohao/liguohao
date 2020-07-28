package cn.liguohao.tool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.liguohao.tool.dao.BBangummiDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BiliBiliTest {

	@Autowired
	private BBangummiDao bangumiDao;
	@Value("${BiliBiliSearchBangumiApi}")
	private String BiliBiliSearchBangumiApi;
	
	
	@Test
	public void BSBApiTest() {
//		String result = HttpUtils.doGet(BiliBiliSearchBangumiApi+"?search_type=media_bangumi&order=click&keyword="+"凡人修仙传");
//		Result resultObj = JSON.parseObject(result,Result.class);
//		JSONObject JsonObj = (JSONObject)resultObj.getData();
//		Bangumi bangumi = JSONObject.toJavaObject((JSONObject) JsonObj.get("result"), Bangumi.class);
//		System.out.println(bangumi.getTitle());
	}
	
	@Test
	public void BangumiDaoTest() {
		//Bangumi bangumi = bangumiDao.findBangumiByKeyword("凡人修仙传");
		//System.out.println(bangumi);
	}
}
