package cn.liguohao.tool.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
* @ClassName: HtmlUnitUtils.java
* @Description: HtmlUnit工具类
* @author: 李国豪
* @date: 2020年5月10日 上午1:11:56
 */
public class HtmlUnitUtils {
	
	/**
	* @Description: 根据url 和 类名 获取特定信息 
	* @param:url-资源地址 
	* @return：Jsonp 的 Document对象
	* @date: 2020年5月10日 上午1:18:24
	 */
	public static Document getDocument(String url) {
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
		 
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
 
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库
        return Jsoup.parse(pageXml);
	}
	/**
	* @Description: 根据url和css查询 获取 指定元素集合
	* @param:url-资源地址  cssQuery-Jsonp的查询语句
	* @return：Jsonp 的 Elements对象
	* @date: 2020年5月10日 上午1:33:39
	 */
	public static Elements getElements(String url, String cssQuery) {
		return HtmlUnitUtils.getDocument(url).select(cssQuery);
	}
	
	/**
	* @Description: 根据url和css 以及 索引 查询 获取 指定元素集合的指定位置元素
	* @param:url-资源地址  cssQuery-Jsonp的查询语句 index-集合中的索引位置
	* @return：返回结果描述
	* @throws：异常描述
	* @date: 2020年5月10日 上午1:39:30
	 */
	public static Element getElementByIndexInElements(String url, String cssQuery, int index) {
		return HtmlUnitUtils.getElements(url, cssQuery).get(index);
	}
	
	/**
	* @Description: 根据url、css、 索引、属性名 获取页面指定位置资源值 
	* @param:url-资源地址  cssQuery-Jsonp的查询语句 index-集合中的索引位置 arrtName-元素的属性名称
	* @return：属性值
	* @date: 2020年5月10日 上午1:42:48
	 */
	public static String getValueByElementAttr(String url, String cssQuery, int index, String arrtName) {
		return HtmlUnitUtils.getElementByIndexInElements(url, cssQuery, index).attr(arrtName);
	}
	
}
