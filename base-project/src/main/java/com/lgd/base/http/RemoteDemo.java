package com.lgd.base.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/14 17:47
 */
public class RemoteDemo {

    public static void main(String[] args) throws IOException {
//        String location = "http://localhost:8100/extStopWordDic/updateStopWord";
//        String jsonStr = "{}";
//        httpResquest();String url,String jsonStr
        //httpResquest();
//        String location = "http://localhost:8100/extStopWordDic/updateStopWord?time=4356";
//        //String jsonStr = "{\"time\":\"1234567\"}";
//        JSONObject jsonParam = new JSONObject();
//        //jsonParam.put("time", "1234567");
//        String json  = httpPostJson(location,jsonParam.toJSONString());
//        System.out.println(json);

//        httpResquest();
        httpJson();
    }

    public static void httpJson() throws IOException {
        final String CONTENT_TYPE_TEXT_JSON = "text/json";
       // HttpClient client = HttpClientBuilder.create().build();
        DefaultHttpClient client = new DefaultHttpClient(
                new PoolingClientConnectionManager());

        String url = "http://localhost:8100/extStopWordDic/updateStopWord?time=1234";
//        String js = "{\"time\":\"1234567\"}";
        String js = "{}";
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("time", "1234567");
//        String js  = jsonParam.toJSONString();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

        StringEntity se = new StringEntity(js);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);

        httpPost.setEntity(se);

        CloseableHttpResponse  response2 = null;

        response2 = client.execute(httpPost);
        HttpEntity entity2 = null;
        entity2 = response2.getEntity();
        String s2 = EntityUtils.toString(entity2, "UTF-8");
        System.out.println(s2);
    }



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
                //LOGGER.warn("状态码为{}。。。", statusCode);
            }
            // Read the response body
            body = EntityUtils.toString(response.getEntity());
           // LOGGER.info("响应结果：\n"+body);
        } catch (IOException e) {
            //LOGGER.error("IOException："+e);
        }
        return body;
    }

    private static void httpResquest(){
        String location = "http://localhost:8100/extStopWordDic/updateStopWord?time=1234567";
        RequestConfig rc = RequestConfig.custom()
                .setConnectionRequestTimeout(10 * 1000)
                .setConnectTimeout(10 * 1000)
                .setSocketTimeout(15 * 1000)
                .build();
        HttpHead head = new HttpHead(location);
        head.setConfig(rc);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(head);
            if (response.getStatusLine().getStatusCode() == 200) { // 返回200 才做操作
                String result= EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(result);
            } else if (response.getStatusLine().getStatusCode() == 304) {
                //return false;
                System.out.println("304");
            } else {
                System.out.printf("remote stopword {} return bad code {}", location,response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            //LOGGER.error("check need reload remote stopword {} error!", e, location);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




    /**
     * 从远程服务器上下载自定义词条
     */
    public static Reader getReader() {
        String location = "http://localhost:9090/my_stopword.dic";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        Reader reader = null;
        RequestConfig rc = RequestConfig.custom()
                .setConnectionRequestTimeout(10 * 1000)
                .setConnectTimeout(10 * 1000).setSocketTimeout(60 * 1000)
                .build();
        CloseableHttpResponse response = null;
        BufferedReader br = null;
        HttpGet get = new HttpGet(location);
        get.setConfig(rc);
        try {
            response = httpclient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String charset = "UTF-8"; // 获取编码，默认为utf-8
                if (response.getEntity().getContentType().getValue()
                        .contains("charset=")) {
                    String contentType = response.getEntity().getContentType()
                            .getValue();
                    charset = contentType.substring(contentType
                            .lastIndexOf("=") + 1);
                }
//                System.out.println(response.getEntity().getContent());
//                response.getEntity().getContent();


                //reader = new InputStreamReader(response.getEntity().getContent(), charset);

				br = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent(), charset));
				List<String> list = new ArrayList<String>();

				String line = null;
				while ((line = br.readLine()) != null) {
					//logger.info("reload remote synonym: {}", line);
					//sb.append(line).append(System.getProperty("line.separator"));
                    list.add(line);
				}
                System.out.println(list.toString());

                //reader = new FastStringReader(sb.toString());
            }
        } catch (IOException e) {
//            logger.error("get remote synonym reader {} error!", e, location);
            throw new IllegalArgumentException(
                    "IOException while reading remote synonyms file", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reader;
    }


}
