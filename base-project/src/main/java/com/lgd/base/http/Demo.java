package com.lgd.base.http;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/29 16:13
 */
public class Demo {

    public static void main(String[] args) {

//        String url = "http://10.250.140.12:8510/frontC/chat";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("source", "zfb_b");
//        jsonObject.put("product", "virtual_card");
//        jsonObject.put("debug", "true");
//        jsonObject.put("ask","你好");
//
//        String result = doPost(url,jsonObject);
//        System.out.println(result);

//        String result = doGet();
//        System.out.println(result);
        doGetReq();
    }


    public static void doGetReq(){
        String urlNameString = "http://localhost:8100/extStopWordDic/updateStopWord?lastTimestamp=1234";
        String result = "";
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(urlNameString);//这里发送get请求
            // 获取当前客户端对象
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(result);
                JSONObject jsonObject = JSONObject.parseObject(result);
                String isUpdate = jsonObject.get("isUpdate").toString();
                System.out.println(isUpdate);
                Boolean isFlag = Boolean.parseBoolean(isUpdate);
                if(isFlag) {
                    JSONArray jsonArray =jsonObject.getJSONArray("stopWordList");
                    System.out.println(jsonArray.get(1).toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String doPost(String url,JSONObject json){
        DefaultHttpClient client = new DefaultHttpClient();
        // 设置超时时间
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpPost post = new HttpPost(url);
//        post.setHeader("Content-type", "application/json; charset=utf-8");
//        post.setHeader("Connection", "Close");
//        String sessionId = "0d6b4fb700e04461897c0c53d7f5962b";
//        post.setHeader("SessionId", sessionId);
        String response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse httpResponse = client.execute(post);
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(httpEntity);// 返回json格式：
                response = result;//JSONObject.f(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    public static String doGet(){
        //String urlTemplate = "http://10.250.140.12:8510/frontC/chat?source=%s&product=%s&debug=true&sessionId=%s&ask=%s";
        //String urlNameString = String.format(urlTemplate,"zfb_b","virtual_card","2C82DCC6584B30A12C24A403859F0633","你好");
        String urlNameString = "http://xmafront.msxf.com/frontC/chat?source=zfb_b&product=virtual_card&debug=true&sessionId=77fb54bc1c73448d860a946b728937e6&ask=一天0.0483%月利率是多少？";
        String result="";
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(urlNameString);//这里发送get请求
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        String answer = jsonObject.getJSONObject("entity").get("answer").toString().replace(",","，").replace("\r\n"," ");

        JSONObject jsonTraceInfo = jsonObject.getJSONObject("entity").getJSONObject("traceInfo");

        String  traceInfoStr = ""+jsonTraceInfo.getObject("classfiyName",String.class)+"&"+
                jsonTraceInfo.getObject("repositoryName",String.class)+"&"+
                jsonTraceInfo.getObject("answerType",String.class)+"&"+
                jsonTraceInfo.getObject("answerDesc",String.class);

        JSONArray jsonArray =jsonObject.getJSONObject("entity").getJSONArray("recommendAnswerList");

        StringBuffer listStr = new StringBuffer("");
        for (int i = 0; i < jsonArray.size(); i++) {
            String answerTitle = jsonArray.getJSONObject(i).getObject("answerTitle",String.class);
            listStr.append(i+"、"+answerTitle+" ");
        }
        System.out.println(answer+listStr);
        //System.out.println(jsonObject.getJSONObject("entity").getJSONArray("recommendAnswerList").getJSONObject(0).get("answerTitle"));
//        String recommendAnswerList = jsonObject.getJSONObject("entity").getObject("recommendAnswerList",String.class)==null?"":jsonObject.getJSONObject("entity").getObject("recommendAnswerList",String.class);
//        System.out.println(answer+":"+traceInfoStr+":"+recommendAnswerList);
//
//        System.out.println("-------------");
//        System.out.println(recommendAnswerList);
//        System.out.println("-------------");
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        String entity = jsonObject.get("entity").toString();
//        JSONObject entityObject = JSONObject.parseObject(entity);
//        System.out.println("answer:"+entityObject.get("answer").toString()+"-traceInfo:"+entityObject.get("traceInfo"));
//
//        JSONObject string = entityObject.getJSONObject("traceInfo");
//        System.out.println(string.toJSONString());


        //JSONObject traceInfoObject = JSONObject.parseObject(entityObject.get("traceInfo").toString());
        //System.out.println("["+traceInfoObject.getJSONObject());

        return result;
    }





}
