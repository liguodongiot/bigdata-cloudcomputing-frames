package com.lgd.base.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/30 15:26
 */
public class EsUrlDemo {

    public static void main(String[] args) throws IOException {
        test1();
        //test3();
        //test2();
    }


    public static void test3() throws IOException {
//        String url = "http://10.250.140.12:9299/_all?pretty";
        //String url = "http://10.250.140.12:9299/_cat/indices";
        String url = "http://10.250.140.12:9299/faq_product_business/_mapping?pretty";
        String str = invokeUrl(url, new HashMap<String, String>(),null, 3000, 3000, "UTF-8",HttpMethod.GET);
        System.out.println(str);
    }

    public static void test2() throws IOException {
        // 接口地址
        String apiURL = "http://10.250.140.12:9299/faq_product_business/_search?pretty";
        HttpClient httpClient = null;
        HttpPost method = null;
        //String parameters = "{}";
        String parameters = "{\"_source\":[\"ask\",\"answer\"],\"query\":{\"match\":{\"ask\":{\"query\":\"怎么贷款\",\"minimum_should_match\":\"80%\"}}},\"timeout\":\"1s\"}";

        //httpClient = new DefaultHttpClient();
        httpClient = HttpClientBuilder.create().build();
        method = new HttpPost(apiURL);

        // 建立一个NameValuePair数组，用于存储欲传送的参数
        method.addHeader("Content-type","application/json; charset=utf-8");
        method.setHeader("Accept", "application/json");
        method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));

        HttpResponse response = httpClient.execute(method);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK) {
        }
        // Read the response body
        String body = EntityUtils.toString(response.getEntity());
        System.out.println(body);
    }

    public static void test1() throws IOException {
        final String CONTENT_TYPE_TEXT_JSON = "text/json";
        DefaultHttpClient client = new DefaultHttpClient(
                new PoolingClientConnectionManager());

        String url = "http://10.250.140.12:9299/faq_product_business/_search?pretty";
//        String url = "http://10.250.140.12:9299/_all?pretty";

        //String js = "{\"_source\":[\"ask\",\"answer\"],\"query\":{\"match\":{\"ask\":{\"query\":\"天气如何\",\"minimum_should_match\":\"80%\"}}},\"timeout\":\"1s\"}";

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

        StringEntity se = new StringEntity("{}");
        se.setContentType(CONTENT_TYPE_TEXT_JSON);

        httpPost.setEntity(se);

        CloseableHttpResponse response2 = null;

        response2 = client.execute(httpPost);
        HttpEntity entity2 = null;
        entity2 = response2.getEntity();
        String s2 = EntityUtils.toString(entity2, "UTF-8");
        System.out.println(s2);
    }
    /**
     * 支持的Http method
     *
     */
    private static enum HttpMethod {
        POST,DELETE,GET,PUT,HEAD;
    };
    private static String invokeUrl(String url, Map params, Map<String,String> headers, int connectTimeout, int readTimeout, String encoding, HttpMethod method){
        //构造请求参数字符串
        StringBuilder paramsStr = null;
        if(params != null){
            paramsStr = new StringBuilder();
            Set<Map.Entry> entries = params.entrySet();
            for(Map.Entry entry:entries){
                String value = (entry.getValue()!=null)?(String.valueOf(entry.getValue())):"";
                paramsStr.append(entry.getKey() + "=" + value + "&");
            }
            //只有POST方法才能通过OutputStream(即form的形式)提交参数
            if(method != HttpMethod.POST){
                url += "?"+paramsStr.toString();
            }
        }

        URL uUrl = null;
        HttpURLConnection conn = null;
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            //创建和初始化连接
            uUrl = new URL(url);
            conn = (HttpURLConnection) uUrl.openConnection();
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            conn.setRequestMethod(method.toString());
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            //设置读取超时时间
            conn.setReadTimeout(readTimeout);
            //指定请求header参数
            if(headers != null && headers.size() > 0){
                Set<String> headerSet = headers.keySet();
                for(String key:headerSet){
                    conn.setRequestProperty(key, headers.get(key));
                }
            }

            if(paramsStr != null && method == HttpMethod.POST){
                //发送请求参数
                out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encoding));
                out.write(paramsStr.toString());
                out.flush();
            }

            //接收返回结果
            StringBuilder result = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
            if(in != null){
                String line = "";
                while ((line = in.readLine()) != null) {
                    result.append(line+"\n");
                }
            }
            return result.toString();
        } catch (Exception e) {
            //处理错误流，提高http连接被重用的几率
            try {
                byte[] buf = new byte[100];
                InputStream es = conn.getErrorStream();
                if(es != null){
                    while (es.read(buf) > 0) {;}
                    es.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (out!=null) {
                    out.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (in !=null) {
                    in.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            if (conn != null){
                conn.disconnect();
            }
        }
        return null;
    }
}
