package com.lgd.es;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/5/25 16:11
 */
public class EsFiveIkFiveTest {

    TransportClient client;

    @Before
    public void testInit() throws Exception {
        client = EsUtils.init();
    }

    @After
    public void after() {
        EsUtils.close();
    }

    String indexName = "liguodong";

    /**
     * 验证
     * curl -XGET http://172.22.1.133:9200/liguodong/_settings?pretty
     *
     */
    @Test
    public void testCreateIndex(){
        //{"analysis":{"analyzer":{"ik":{"tokenizer":"ik_smart"}}}}
        String ikIndex = "{\"analysis\":{\"analyzer\":{\"ik\":{\"tokenizer\":\"ik_max_word\"}}}}";

        //方式一
        //新建索引
        //client.admin().indices().prepareCreate(indexName).get();

        //方式二
        //ES是区分大小写的，索引所有数据在进到ES之前，最好都规格化。
        //setSettings挺有用，比如上面说的设置分片数就是通过这里设置的，参数可以是JSON，也有其他的重载；
        //新建索引的时候配置默认的分词器IK
        CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).setSettings(ikIndex).get();
        System.out.println(response.toString());

    }





}
