package com.lgd.es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

/**
 * Describe: 测试反射初始化方式
 *
 * @author: guodong.li
 * @datetime: 2017/5/24 19:33
 */
public class ReflexTest {

    @Test
    public void testReflex() {
        String index = "xma_faq_dev";
        String type = "correct_answer_info";
        EsReflexUtils.init();
        TransportClient transportClient = EsReflexUtils.getTransportClient();
        GetResponse response = transportClient.prepareGet(index, type, "2").execute().actionGet();
        System.out.println(response.getSourceAsString());

        EsReflexUtils.close();

    }


}
