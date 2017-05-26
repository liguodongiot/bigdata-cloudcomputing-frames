package com.lgd.es;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe: 测试反射初始化方式
 *
 * @author: guodong.li
 * @datetime: 2017/5/24 19:33
 */
public class ReflexTest {

    String index = "xma_faq_dev";
    String type = "correct_answer_info";
    TransportClient client;

    @Before
    public void before() throws Exception {
        EsReflexUtils.init();
        client = EsReflexUtils.getTransportClient();
    }
    @After
    public void after() {
        EsReflexUtils.close();
    }

    @Test
    public void testReflex() {

        TransportClient transportClient = EsReflexUtils.getTransportClient();
        GetResponse response = transportClient.prepareGet(index, type, "2").execute().actionGet();
        System.out.println(response.getSourceAsString());

    }

    /**
     * 创建索引时
     * 设置分片数和副本数（分片数设置了不能修改）
     */
    @Test
    public void testShardAndReplicas() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("number_of_shards",4);
        map.put("number_of_replicas",2);

        CreateIndexRequestBuilder builder = client.admin()
                .indices().prepareCreate("liguodong");
        builder.setSettings(map);
        CreateIndexResponse actionGet = builder.execute().actionGet();
        System.out.println(actionGet.toString());

    }

    /**
     * 修改副本数
     */
    @Test
    public void testReplicas() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("number_of_replicas",0);
        UpdateSettingsRequestBuilder builder = client.admin()
                .indices().prepareUpdateSettings("liguodong");
        builder.setSettings(map);
        UpdateSettingsResponse response = builder.execute().actionGet();
    }

    /**
     * ES分片查询方式
     *
     * 默认是randomize across shards 随机选取，表示随机的从分片中取数据
     * _local：指查询操作会优先在本地节点有的分片中查询，没有的话再在其它节点查询。
     * _primary：指查询只在主分片中查询
     * _primary_first：指查询会先在主分片中查询，如果主分片找不到（挂了），就会在副本中查询。
     * _only_node：指在指定id的节点里面进行查询，如果该节点只有要dx查询索引的部分分片，就只在这部分分片中查找，
     *  所以查询结果可能不完整。如_only_node:123在节点id为123的节点中查询。
     * _prefer_node:nodeid 优先在指定的节点上执行查询
     * _shards:0 ,1,2,3,4：查询指定分片的数据
     * 自定义：_only_nodes：根据多个节点进行查询
     */
    @Test
    public void testShardQuery() {
       SearchRequestBuilder builder = client.prepareSearch("megacorp").setTypes("employee")
               .setPreference("_primary_first");

       SearchResponse response = builder.execute().actionGet();
       SearchHits hits = response.getHits();
       long totalHits = hits.getTotalHits();
       System.out.println("总数："+totalHits);
       SearchHit[] searchHit = hits.getHits();

       for (SearchHit hit:searchHit) {
           System.out.println(hit.getSourceAsString());
       }


    }


}
