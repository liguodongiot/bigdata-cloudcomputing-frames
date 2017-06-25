package com.lgd.es.util;

import javafx.util.Builder;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;

import java.io.Serializable;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/21 20:50
 */
public class EsParam implements Serializable {

    private static final long serialVersionUID = 3806157555158678120L;

    private TransportClient client;
    //索引库
    private String indexName;
    //索引库别名
    private String aliasesName;
    //分片数
    private Integer shards;
    //副本
    private Integer replicas;

    public TransportClient getClient() {
        return client;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getAliasesName() {
        return aliasesName;
    }

    public Integer getShards() {
        return shards;
    }

    public Integer getReplicas() {
        return replicas;
    }


    public static class ParamBuilder implements Builder<EsParam> {

        private TransportClient client;
        private String indexName;

        private String aliasesName;
        private Integer shards = 3;
        private Integer replicas = 1;


        public ParamBuilder setAliasesName(String aliasesName) {
            this.aliasesName = aliasesName;
            return this;
        }

        public ParamBuilder setShards(Integer shards) {
            this.shards = shards;
            return this;
        }

        public ParamBuilder setReplicas(Integer replicas) {
            this.replicas = replicas;
            return this;
        }

        public ParamBuilder(TransportClient client, String indexName){
            this.client = client;
            this.indexName = indexName;
        }


        public EsParam build() {
            return new EsParam(this);
        }
    }

    private EsParam(ParamBuilder builder){
        this.client = builder.client;
        this.indexName = builder.indexName;
        this.aliasesName = StringUtils.isBlank(builder.aliasesName)?builder.indexName+"_alias":builder.aliasesName;
        this.replicas = builder.replicas;
        this.shards = builder.shards;
    }

}
