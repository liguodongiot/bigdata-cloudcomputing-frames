package com.lgd.base.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/29 16:40
 */
public class DemoMain {

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url        请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params) {
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        for (Iterator it = params.entrySet().iterator(); it.hasNext();) {

        }
        //设置Http Post数据
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("执行HTTP Post请求" + url + "时，发生异常！", e.toString());
        } finally {
            method.releaseConnection();
        }

        return response;
    }

    public static void main(String[] args) {
        String x = doPost("http://lavasoft.blog.51cto.com/62575/64994", new HashMap());
        System.out.println(x);
    }
}
