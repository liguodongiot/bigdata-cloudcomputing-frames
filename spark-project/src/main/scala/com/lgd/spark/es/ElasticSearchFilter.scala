package com.lgd.spark.es

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

/**
  * Created by liguodong on 2017/2/26.
  */
object ElasticSearchFilter {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ElasticSearchFilter").setMaster("local")

    //多个用逗号分割
    conf.set("es.nodes", "192.168.133.252")
    conf.set("es.port", "9200")
    //索引
    conf.set("es.index.auto.create", "true")

    val sc = new SparkContext(conf)

    val start = 183302989
    val end = 187690437
    val tp = 10

    //查询event_type=10 并且 item_id>= start && item_id<end
    val query: String = s"""{
     "query": {"match_all": {}},
     "filter" : {
        "bool": {
          "must": [
              {"term" : {"event_type" : $tp}},
              {
              "range": {
                "item_id": {
                "gte": "$start",
                "lt": "$end"
                }
              }
            }
          ]
        }
     }
    }"""

    //指索引和查询条件
    val rdd1 = sc.esRDD("level-one-2017.02.19", query)
    //_id
    val rdd2 = rdd1.map(_._1)
    println(rdd2.collect().toBuffer)

    //val rdd1 = sc.esRDD("store")
    println(rdd1.collect().toBuffer)

    println(rdd1.collect().size)

  }


}
