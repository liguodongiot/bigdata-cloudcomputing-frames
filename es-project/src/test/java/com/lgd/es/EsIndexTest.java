package com.lgd.es;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.lgd.es.util.EsParam;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Before;
import org.junit.Test;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/21 16:37
 */
public class EsIndexTest {

    TransportClient client;

    @Before
    public void testInit() throws Exception {
        client = EsUtils.init();
    }


    /**
     * 创建索引库
     * curl -XGET  http://172.22.1.28:9200/_cat/indices
     * @throws Exception
     */
    @Test
    public void testIndexes1() throws Exception {
        CreateIndexResponse response = client.admin().indices().prepareCreate("twitter3").get();
        System.out.println(response.remoteAddress().getHost()+"-"+
                response.remoteAddress().getAddress()+"-"+
                response.remoteAddress().getPort()
        );
        System.out.println(response.isAcknowledged());
    }


    /**
     * 设置setting
     * curl -XGET  http://172.22.1.28:9200/twitter/_settings?pretty
     * @throws Exception
     */
    @Test
    public void testIndexes2() throws Exception {
//        DeleteIndexRequestBuilder builder = client.admin().indices().prepareDelete("twitter");
//        client.admin().indices().delete(builder.request()).get();

        IndicesExistsResponse response = client.admin().indices().prepareExists("twitter").get();

        if(response.isExists()){
            client.admin().indices().prepareDelete("twitter")
                    .setTimeout(TimeValue.timeValueSeconds(1))
                    .get();
        }

        client.admin().indices().prepareCreate("twitter")
                .setSettings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2))
                .get();

    }


//    client.admin().indices().prepareCreate("twitter")
//    .addMapping("tweet", "{\n" +
//                "    \"tweet\": {\n" +
//                "      \"properties\": {\n" +
//                "        \"message\": {\n" +
//                "          \"type\": \"string\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  }")
//            .get();
    /**
     * 设置mapping
     * curl -XGET  http://172.22.1.28:9200/twitter?pretty
     *
     * @throws Exception
     */
    @Test
    public void testIndexes3() throws Exception {
        IndicesExistsResponse response = client.admin().indices().prepareExists("twitter").get();

        if(response.isExists()){
            client.admin().indices().prepareDelete("twitter")
                    .setTimeout(TimeValue.timeValueSeconds(1))
                    .get();
        }

        client.admin().indices().prepareCreate("twitter")
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 3)
                        .put("index.number_of_replicas", 2))
                .addMapping("tweet", "{\n" +
                        "    \"tweet\": {\n" +
                        "      \"properties\": {\n" +
                        "        \"message\": {\n" +
                        "          \"type\": \"string\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }")
                .get();
    }


    /**
     * 添加索引库类型
     *
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {

        client.admin().indices().preparePutMapping("twitter")
                .setType("user")
                .setSource("{\n" +
                        "  \"properties\": {\n" +
                        "    \"name\": {\n" +
                        "      \"type\": \"string\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .get();

        // You can also provide the type in the source document
        client.admin().indices().preparePutMapping("twitter")
                .setType("user")
                .setSource("{\n" +
                        "    \"user\":{\n" +
                        "        \"properties\": {\n" +
                        "            \"name\": {\n" +
                        "                \"type\": \"string\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")
                .get();
    }

    /**
     * 更新已存在的mapping 在类型中添加一个字段
     *
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {

        client.admin().indices().preparePutMapping("twitter")
                .setType("user")
                .setSource("{\n" +
                        "  \"properties\": {\n" +
                        "    \"user_name\": {\n" +
                        "      \"type\": \"string\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .get();

    }


    /**
     * 刷新索引库
     *
     * @throws Exception
     */
    @Test
    public void test6() throws Exception {

        //Refresh all indices
        client.admin().indices().prepareRefresh().get();

        //Refresh one index
        client.admin().indices()
                .prepareRefresh("twitter")
                .get();

        //Refresh many indices
        client.admin().indices()
                .prepareRefresh("twitter", "faq_question_answer_chat")
                .get();
    }


    /**
     * 更新索引库setting
     *
     * @throws Exception
     */
    @Test
    public void test7() throws Exception {

        UpdateSettingsResponse resonse = client.admin().indices().prepareUpdateSettings("twitter")
                .setSettings(Settings.builder()
                        .put("index.number_of_replicas", 0)
                )
                .get();

        System.out.println(resonse.toString());

    }

    /**
     * 获取setting
     *
     * @throws Exception
     */
    @Test
    public void test8() throws Exception {

        GetSettingsResponse response = client.admin().indices()
                .prepareGetSettings("faq_question_answer_chat", "twitter").get();

        for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) {
            String index = cursor.key;
            Settings settings = cursor.value;
            Integer shards = settings.getAsInt("index.number_of_shards", null);
            Integer replicas = settings.getAsInt("index.number_of_replicas", null);
            System.out.println(index+"---"+settings+"---"+shards+"---"+replicas);
        }

    }


//    {
//        "liguodong_v1": {
//        "aliases": {
//            "liguodong": {}
//        },
//        "mappings": {},
//        "settings": {
//            "index": {
//                "creation_date": "1498319533736",
//                        "number_of_shards": "1",
//                        "number_of_replicas": "0",
//                        "uuid": "OrIDXjYkQdezVPZ2KVAlfw",
//                        "version": {
//                    "created": "5020099"
//                },
//                "provided_name": "liguodong_v1"
//            }
//        }
//    }
//    }
    /**
     *
     * curl -XGET  http://172.22.1.28:9200/faq_animus_recognition_info?pretty
     *
     * GET /liguodong_v1?pretty
     * GET /liguodong_v1/_alias/*
     * @throws Exception
     */
    @Test
    public void test9() throws Exception {

        EsParam esParam = new EsParam.ParamBuilder(client,"liguodong_v2")
                .setShards(1)
                .setReplicas(0)
                .build();
        com.lgd.es.util.EsUtils.deleteIndexLib(esParam);
        com.lgd.es.util.EsUtils.createIndexLib(esParam);

    }


    @Test
    public void test10() throws Exception {

        com.lgd.es.util.EsUtils.replaceIndexLibByAliasesName(client,"liguodong_v1",
                "liguodong_v2",
                "liguodong");

    }

    @Test
    public void test11() throws Exception {
        EsParam esParam = new EsParam.ParamBuilder(client,"liguodong_v2")
                .setShards(1)
                .setReplicas(0)
                .build();
        com.lgd.es.util.EsUtils.deleteDocumentByQuery(esParam);
    }

}
