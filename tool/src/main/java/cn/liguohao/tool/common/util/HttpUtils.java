package cn.liguohao.tool.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
* @ClassName: HttpUtils.java
* @Description: HttpCilent工具类
* @author: 李国豪
* @date: 2020年5月9日 下午5:52:10
 */
@Component
public class HttpUtils {
	private static PoolingHttpClientConnectionManager cm;

    static {
        cm = new PoolingHttpClientConnectionManager();

        //设置最大连接数
        cm.setMaxTotal(100);

        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
    }
    
    /**
     * @Title: doGet
     * @Description: get请求
     * @param url 请求地址
     * @return 结果字符串
     * @return String 返回类型
     * @throws
      */
     public static String doGet(String url){
         //获取HttpClient对象
         CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

         //创建httpGet请求对象，设置url地址
         HttpGet httpGet = new HttpGet(url);

         //设置请求信息
         httpGet.setConfig(getConfig());

         CloseableHttpResponse response = null;



         try {
             //使用HttpClient发起请求，获取响应
             response = httpClient.execute(httpGet);

             //解析响应，返回结果
             if (response.getStatusLine().getStatusCode() == 200) {
                 //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                 if (response.getEntity() != null) {
                     String content = EntityUtils.toString(response.getEntity(), "utf8");
                     return content;
                 }
             }

         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             //关闭response
             if (response != null) {
                 try {
                     response.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
         //返回空串
         return "";
     }
    
    /**
    * @Title: doGet
    * @Description: get请求
    * @param url 请求地址
    * @param refer 
    * @return 结果字符串
    * @return String 返回类型
    * @throws
     */
    public static String doGet(String url,String refer){
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        //创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(getConfig());
        //设置头信息
        httpGet = setHeader(httpGet, refer);

        CloseableHttpResponse response = null;



        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回空串
        return "";
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
    public static String doGetHtml(String url) {
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        //创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(getConfig());
        //设置头信息
        httpGet = setHeader(httpGet);

        CloseableHttpResponse response = null;


        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回空串
        return "";
    }


    /**
     * 下载图片
     *
     * @param url
     * @return 图片名称
     */
    public static String doGetImage(String url,String filePath) {
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        //创建httpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;


        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否不为空
                if (response.getEntity() != null) {
                    //下载图片
                    //获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));

                    //创建图片名，重命名图片
                    String picName = UUID.randomUUID().toString() + extName;

                    //下载图片
                    //声明OutPutStream
                    OutputStream outputStream = new FileOutputStream(new File(filePath +"/"+
                            picName));
                    response.getEntity().writeTo(outputStream);

                    //返回图片名称
                    return picName;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果下载失败，返回空串
        return "";
    }

    //设置请求信息
    private static RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)    //创建连接的最长时间
                .setConnectionRequestTimeout(500)  // 获取连接的最长时间
                .setSocketTimeout(10000)    //数据传输的最长时间
                .build();

        return config;
    }
    //设置头信息
    private static HttpGet setHeader(HttpGet httpGet,String referer){
        httpGet.setHeader("Referer",referer);
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;");
        httpGet.setHeader("Accept-Language","zh-cn");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
        httpGet.setHeader("Keep-Alive", "300");
        return httpGet;
    }
    private static HttpGet setHeader(HttpGet httpGet){
        httpGet.setHeader("Referer","no-referrer");
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;");
        httpGet.setHeader("Accept-Language","zh-cn");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
        httpGet.setHeader("Keep-Alive", "300");
        return httpGet;
    }

}
