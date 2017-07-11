package com.lgd.es.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Describe: 通过HTTP请求方式
 * author: guodong.li
 * datetime: 2017/6/30 17:43
 */
public class EsUrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsUrlUtils.class);

    public static String httpPostJson(String url,String jsonStr){

        String body = null;
        //HttpClient httpClient = new DefaultHttpClient();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost method = new HttpPost(url);

        // 建立一个NameValuePair数组，用于存储欲传送的参数
        method.addHeader("Content-type","application/json; charset=utf-8");
        method.setHeader("Accept", "application/json");
        method.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));

        try {
            HttpResponse response = httpClient.execute(method);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.warn("状态码为{}。。。", statusCode);
            }
            // Read the response body
            body = EntityUtils.toString(response.getEntity());
            LOGGER.info("响应结果：\n"+body);
        } catch (IOException e) {
            LOGGER.error("IOException："+e);
        }
        return body;
    }


    private static String httpNoParams(String url){

        String line;
        URL uUrl;
        HttpURLConnection conn = null;
        BufferedReader in = null;

        //接收返回结果
        StringBuilder result = new StringBuilder();
        try {
            //创建和初始化连接
            uUrl = new URL(url);
            conn = (HttpURLConnection) uUrl.openConnection();
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间
            conn.setConnectTimeout(3000);
            //设置读取超时时间
            conn.setReadTimeout(3000);

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            while ((line = in.readLine()) != null) {
                result.append(line+"\n");
            }

        } catch (Exception e) {
            LOGGER.error("IO Stream handle exception..."+e);
        } finally {

            try {
                if (in !=null) {
                    in.close();
                }
                //关闭连接
                if (conn != null){
                    conn.disconnect();
                }
            }catch (Exception e) {
                LOGGER.error("IO Stream close exception..."+e);
            }
        }
        return result.toString();
    }




    public static void main(String[] args) {
        // 接口地址
//        String apiURL = "http://10.250.140.12:9299/faq_product_business/_search?pretty";
//        String parameters = "{\"_source\":[\"ask\",\"answer\"],\"query\":{\"match\":{\"ask\":{\"query\":\"天气好呀\",\"minimum_should_match\":\"80%\"}}},\"timeout\":\"1s\"}";
//        httpPostJson(apiURL,parameters);

//        String url = "http://10.250.140.12:9299/faq_product_business/_mapping?pretty";
//        String str = httpNoParams(url);
//        System.out.println(str);

        EsUrlUtils esUrlUtils = new EsUrlUtils();
        System.out.println(esUrlUtils.getClass().getSimpleName());
    }


}
