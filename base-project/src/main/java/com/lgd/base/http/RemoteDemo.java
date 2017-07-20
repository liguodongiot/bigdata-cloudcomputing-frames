package com.lgd.base.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/14 17:47
 */
public class RemoteDemo {

    public static void main(String[] args) {
        getReader();
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
