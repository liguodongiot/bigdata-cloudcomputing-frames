package com.lgd.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/5/22 8:26
 */
public class Es5Test {

    TransportClient client;

    @Before
    public void testInit() throws Exception {
        client = EsUtils.init();
    }

    String index = "sichuan";
    String type = "chengdu";

    /**
     * 创建索引--json
     * @throws Exception
     */
    @Test
    public void testIndex1() throws Exception {

        String jsonStr = "{\"name\":\"ls\",\"age\":10}";
        IndexResponse response = client.prepareIndex(index, type, "2").setSource(jsonStr).execute().actionGet();
        System.out.println(response.getId());
    }

    /**
     * 创建索引--map
     * @throws Exception
     */
    @Test
    public void testIndex2() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("name", "中国人非常棒");
        hashMap.put("age", 36);
        IndexResponse response = client.prepareIndex(index, type, "39").setSource(hashMap).execute().actionGet();
        System.out.println(response.getId()+":"+response.getVersion());
        //response.getShardInfo().ge
    }

    /**
     * 创建索引--bean
     * @throws Exception
     */
    @Test
    public void testIndex3() throws Exception {
        Person person = new Person();
        person.setName("wintfru");
        person.setAge(10);
        person.setSalary(8999.23);

        ObjectMapper objectMapper = new ObjectMapper();
        IndexResponse response = client.prepareIndex(index, type, "4")
                .setSource(objectMapper.writeValueAsString(person))
                .execute().actionGet();
        System.out.println(response.getId());
    }

    /**
     * 创建索引--es 工具类
     * @throws Exception
     */
    @Test
    public void testIndex4() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("name", "ww").field("age",20)
                .endObject();
        IndexResponse response = client.prepareIndex(index, type, "3")
                .setSource(builder).execute().actionGet();
        System.out.println(response.getId());
    }

    /**
     * 查询
     * @throws UnknownHostException
     */
    @Test
    public void testGet1() throws UnknownHostException {

        TransportClient transportClient = EsUtils.init();
        //搜索数据
        GetResponse response = transportClient.prepareGet("blog", "article", "1")
                .execute()
                .actionGet();
        //输出结果
        System.out.println(response.getSourceAsString());
        //关闭client
        transportClient.close();
    }


    /**
     * 查询-get
     * @throws Exception
     */
    @Test
    public void testGet2() throws Exception {
        GetResponse response = client.prepareGet(index, type, "2").execute().actionGet();
        System.out.println(response.getSourceAsString());
    }


    /**
     * 更新 --1
     * @throws Exception
     */
    @Test
    public void testUpdate1() throws Exception {
        UpdateRequest request = new UpdateRequest(index, type, "2");
        request.doc(XContentFactory.jsonBuilder()
                .startObject().field("age", 88).endObject());
        UpdateResponse response = client.update(request ).get();
        System.out.println(response.getVersion());
    }

    /**
     * 更新--2
     * @throws Exception
     */
    @Test
    public void testUpdate2() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject().field("name", "james").endObject();

        UpdateResponse response = client.prepareUpdate(index, type, "2").setDoc(builder).get();
        System.out.println(response.getVersion());
    }


    /**
     * 更新或者插入
     * @throws Exception
     */
    @Test
    public void testInsertOrUpdate() throws Exception {

        UpdateRequest request = new UpdateRequest(index, type, "11");
        request.doc(XContentFactory.jsonBuilder()
                .startObject().field("name", "kevin")
                .endObject());
        //没有数据就插入数据
        request.upsert(XContentFactory.jsonBuilder()
                .startObject().field("name", "stefan")
                .field("age", 23)
                .endObject());
        UpdateResponse response = client.update(request).get();
        System.out.println(response.getVersion());
    }

    /**
     * 删除
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        DeleteRequest request = new DeleteRequest();
        request.index(index);
        request.type(type);
        request.id("37");
        DeleteResponse response = client.delete(request).get();
        System.out.println(response.getVersion());

        System.out.println(response.getResult().getLowercase().equals("deleted"));
    }


    /**
     * 批量操作bulk
     * 非常适合做一些数据批量处理
     * @throws Exception
     */
    @Test
    public void testBulk() throws Exception {
        BulkRequestBuilder prepareBulk = client.prepareBulk();

        //创建索引
        IndexRequest indexrequest = new IndexRequest(index, type, "12");
        indexrequest.source(XContentFactory.jsonBuilder()
                .startObject()
                .field("name", "anglelababy")
                .field("age", 12)
                .endObject());
        //删除索引
        DeleteRequest deleteRequest = new DeleteRequest(index, type, "3");

        prepareBulk.add(indexrequest);
        prepareBulk.add(deleteRequest);

        BulkResponse response = prepareBulk.execute().actionGet();
        if(response.hasFailures()){
            System.out.println("出现执行失败的语句");
            BulkItemResponse[] items = response.getItems();
            for (BulkItemResponse bulkItemResponse : items) {
                System.out.println(bulkItemResponse.getFailureMessage());
            }
        }else{
            System.out.println("全部执行成功");
        }
    }


    /**
     * 查询-search
     * @throws Exception
     */
    @Test
    public void testSearch() throws Exception {

        SearchResponse response = client
                //指定索引库
                .prepareSearch(index)
                //指定类型
                .setTypes(type)
                //指定查询类型
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                //指定要查询的关键字
                .setQuery(QueryBuilders.matchQuery("name", "中国人"))
                //相当于实现分页
                .setFrom(0)
                .setSize(10)
                //执行查询
                .execute().actionGet();
        //可以获取查询的所有内容
        SearchHits hits = response.getHits();
        //获取查询的数据总数
        long totalHits = hits.getTotalHits();
        System.out.println("总数："+totalHits);
        SearchHit[] hitsArr = hits.getHits();
        for (SearchHit searchHit : hitsArr) {
            System.out.println(searchHit.getSourceAsString());
        }
    }


    /**
     * 排序
     * @throws Exception
     */
    @Test
    public void testSort() throws Exception {
        SearchResponse response = client
                //指定索引库
                .prepareSearch(index)
                //指定类型
                .setTypes(type)
                //指定查询类型
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                //指定要查询的关键字
                .setQuery(QueryBuilders.matchQuery("name", "中国人"))
                //添加排序字段并指定排序类型
                .addSort("age", SortOrder.DESC)
                //相当于实现分页
                .setFrom(0)
                .setSize(10)
                //执行查询
                .execute().actionGet();

        //可以获取查询的所有内容
        SearchHits hits = response.getHits();
        //获取查询的数据总数
        long totalHits = hits.getTotalHits();
        System.out.println("总数："+totalHits);
        SearchHit[] hitsArr = hits.getHits();
        for (SearchHit searchHit : hitsArr) {
            System.out.println(searchHit.getSourceAsString());
        }

    }

    /**
     * 过滤
     * lt：小于
     * lte:小于等于
     * gt:大于
     * gte:大于等于
     *
     * QueryBuilders.rangeQuery("age").from(12).to(18)
     * @throws Exception
     */
    @Test
    public void testFilter() throws Exception {
        SearchResponse response = client
                //指定索引库
                .prepareSearch(index)
                //指定类型
                .setTypes(type)
                //指定查询类型
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setPostFilter(QueryBuilders.rangeQuery("age").gte(25).lte(35))
                //相当于实现分页
                .setFrom(0)
                .setSize(10)
                //执行查询
                .execute().actionGet();

        //可以获取查询的所有内容
        SearchHits hits = response.getHits();
        //获取查询的数据总数
        long totalHits = hits.getTotalHits();
        System.out.println("总数："+totalHits);
        SearchHit[] hitsArr = hits.getHits();
        for (SearchHit searchHit : hitsArr) {
            System.out.println(searchHit.getSourceAsString());
        }

    }


    /**
     * 根据查询条件进行删除数据
     * 长期运行的操作
     * @throws Exception
     */
    @Test
    public void testDeleteForQuery() throws Exception {
        BulkIndexByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchQuery("age", 88)) //query
                        .source(index) //index
                        .get(); //execute the operation
        long deleted = response.getDeleted(); //number of deleted documents
        System.out.println(deleted);
    }

    /**
     * 如果您想异步地执行它，您可以调用execute并提供一个侦听器而不是get.
     * @throws Exception
     */
    @Test
    public void testDeleteAsynchronousForQuery() throws Exception {
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
            .filter(QueryBuilders.matchQuery("name", "中国人")) //query
            .source(index) //index
            .execute(new ActionListener<BulkIndexByScrollResponse>() { //listener
                public void onResponse(BulkIndexByScrollResponse response) {
                    long deleted = response.getDeleted(); //number of deleted documents
                    System.out.println("deleted:" + deleted);
                }
                public void onFailure(Exception e) {
                    // Handle the exception
                }
            });
    }

    @Test
    public void testDsl() {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name","wintfru");

        String script = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"name\" : \"{{name}}\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        SearchRequest searchRequest = new SearchRequest(index).types(type);
        SearchResponse sr = new SearchTemplateRequestBuilder(client)
                .setScript(script)
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(params)
                .setRequest(searchRequest)
                .get()
                .getResponse();

        //List<Person> rets = new ArrayList<Person>((int)sr.getHits().getTotalHits());

        for (SearchHit hit : sr.getHits()) {
            Map<String, Object> source = hit.getSource();
            System.out.println(source.get("name").toString());
            System.out.println(source.get("age").toString());
        }

    }

    String indexAnswer = "ddd_faq_dev";
    String typeAnswer = "correct_answer_info";
    @Test
    public void testAnswerDsl() {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ask","绑定");

        String script = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"ask\" : \"{{ask}}\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        SearchRequest searchRequest = new SearchRequest(indexAnswer).types(typeAnswer);
        SearchResponse sr = new SearchTemplateRequestBuilder(client)
            .setScript(script)
            .setScriptType(ScriptType.INLINE)
            .setScriptParams(params)
            .setRequest(searchRequest)
            .get()
            .getResponse();

        for (SearchHit hit : sr.getHits()) {
            Map<String, Object> source = hit.getSource();
            System.out.println(source.get("ask").toString());
            System.out.println(source.get("answer").toString());
        }

    }


    @Test
    public void testAskAnswerDsl() {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key_word","绑定");

        /**
         {
             "query" : {
                 "bool": {
                     "should": [
                        { "match": { "ask": "Smith" }},
                        { "match": { "answer": "Smith"}}
                     ]
                 }
             }
         }
         */
        String script = "{\n" +
                "\t\"query\" : {\n" +
                "\t\t\"bool\": {\n" +
                "\t\t\t\"should\": [\n" +
                "\t\t\t\t{ \"match\": { \"ask\": \"{{key_word}}\" }},\n" +
                "\t\t\t\t{ \"match\": { \"answer\": \"{{key_word}}\"}}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        SearchRequest searchRequest = new SearchRequest(indexAnswer).types(typeAnswer);
        SearchResponse sr = new SearchTemplateRequestBuilder(client)
                .setScript(script)
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(params)
                .setRequest(searchRequest)
                .get()
                .getResponse();

        for (SearchHit hit : sr.getHits()) {
            Map<String, Object> source = hit.getSource();
            System.out.println(source.get("ask").toString());
            System.out.println(source.get("answer").toString());
        }

    }




}
