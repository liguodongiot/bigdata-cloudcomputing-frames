package com.lgd.es.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesAction;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/21 19:50
 */
public class EsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsUtils.class);

    public static void createIndexLib(EsParam esParam) {
        String aliasName = String.format("{\"%s\":{}}",esParam.getAliasesName());
        //String aliasName = "{\"liguodong\":{}}";
        System.out.println(aliasName);
        CreateIndexResponse response = esParam.getClient().admin().indices()
                .prepareCreate(esParam.getIndexName())
                .setAliases(aliasName)
                .setSettings(Settings.builder()
                    .put("index.number_of_shards", esParam.getShards())
                    .put("index.number_of_replicas", esParam.getReplicas()))
                .get();
        LOGGER.info("host:"+response.remoteAddress().getHost()+
                ",address:"+ response.remoteAddress().getAddress()+
                ",port:"+ response.remoteAddress().getPort()+
                ",indexName:"+esParam.getIndexName()+" is created...");
    }

    public static void deleteIndexLib(EsParam esParam) {
        if(isExistsIndexLib(esParam)){
            DeleteIndexResponse deleteResponse = esParam.getClient().admin().indices().prepareDelete(esParam.getIndexName())
                    .setTimeout(TimeValue.timeValueSeconds(1))
                    .get();
            LOGGER.info("host:"+deleteResponse.remoteAddress().getHost()+
                    ",address:"+ deleteResponse.remoteAddress().getAddress()+
                    ",port:"+ deleteResponse.remoteAddress().getPort()+
                    ",indexName:"+esParam.getIndexName()+" is deleted...");
        }
    }

    public static boolean isExistsIndexLib(EsParam esParam) {
        IndicesExistsResponse response = esParam.getClient().admin().indices().prepareExists(esParam.getIndexName()).get();
        return response.isExists();
    }

    public static void addIndexMappingsType(EsParam esParam,String typeName,String fieldName){
        PutMappingResponse response = esParam.getClient().admin().indices()
                .preparePutMapping(esParam.getIndexName())
                .setType(typeName)
                .setSource(fieldName)
                .get();
        LOGGER.info("host:"+response.remoteAddress().getHost()+
                ",address:"+ response.remoteAddress().getAddress()+
                ",port:"+ response.remoteAddress().getPort()+
                ",indexName:"+esParam.getIndexName()+
                ",indexType:"+typeName+" is add...");
    }

    public static void indexDocument(TransportClient client, DocumentParam documentParam){

        ObjectMapper objectMapper = new ObjectMapper();
        IndexResponse response = null;
        try {
            response = client.prepareIndex(documentParam.getIndexName(),
                    documentParam.getTypeName(),
                    documentParam.getDocumentId())
                    .setSource(objectMapper.writeValueAsString(documentParam.getDocument()))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("索引文档到ES索引库出错。。。"+e);
        }
        LOGGER.info("indexDocument: 索引文档[{}]结束。。。",response.getId());
    }


    public static void updateIndexLibSettings(EsParam esParam){
        UpdateSettingsResponse response = esParam.getClient().admin().indices()
                .prepareUpdateSettings(esParam.getIndexName())
                .setSettings(Settings.builder().put("index.number_of_replicas", 0))
                .get();
        LOGGER.info("update indexLib {}, acknowledged:{}.", esParam.getIndexName(),response.isAcknowledged());
    }

    public static void replaceIndexLibByAliasesName(TransportClient client, String oldIndexName,
                                                   String newIndexName, String aliasesName){
        IndicesAliasesResponse response = client.admin().indices().prepareAliases()
                .addAlias(newIndexName, aliasesName)
                .removeAlias(oldIndexName, aliasesName)
                .get();
        LOGGER.info("replace oldIndex:[{}] to newIndex:[{}] is {}",oldIndexName,newIndexName,response.isAcknowledged());
    }

    public static void addIndex(TransportClient client,String indexName,String text){
//        client.admin().indices().prepareAnalyze(indexName,text)
//                .get();
    }


    public static void ik(EsParam esParam){
        IndicesAdminClient indicesAdminClient = esParam.getClient().admin().indices();
        AnalyzeRequestBuilder request = new AnalyzeRequestBuilder(indicesAdminClient, AnalyzeAction.INSTANCE,"faq_product_business","中华人民共和国国歌");
        //request.setAnalyzer("ik_smart");
        request.setTokenizer("ik_smart");
        //request.setTokenizer("ik_max_word");

        // Analyzer（分析器）、Tokenizer（分词器）
        List<AnalyzeResponse.AnalyzeToken> listAnalysis = request.execute().actionGet().getTokens();
        System.out.println(listAnalysis.get(0).getTerm());
    }


    public static void deleteDocumentByQueryId(EsParam esParam) {
        DeleteByQueryAction.INSTANCE.newRequestBuilder(esParam.getClient())
                .filter(QueryBuilders.termQuery("id", 1L))
                .source("faq_question_answer_chat")
                .execute(new ActionListener<BulkIndexByScrollResponse>() {

                    public void onResponse(BulkIndexByScrollResponse response) {
                        long deleted = response.getDeleted();
                        System.out.println(deleted);
                    }

                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });

    }



}
