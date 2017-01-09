package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/8.
  */
object WordCount{

  def main(args: Array[String]): Unit = {

    val input_path = args(0)
    val output_path = args(1)
//    val input_path = "/input"
//    val output_path = "/out100"

    val conf = new SparkConf().setAppName("WordCount")

//    conf.setMaster("spark://192.168.133.252:7077")
//      .setJars(List("E:\\sourceCode\\bigdata-cloudcomputing-frames\\spark-project\\target\\spark-1.0.jar"))

    //非常重要，是通向Spark集群的入口
    val sc = new SparkContext(conf)

    sc.textFile(input_path).flatMap(_.split(" "))
      .map((_,1)).reduceByKey(_+_).sortBy(_._2,true).saveAsTextFile(output_path)

    sc.stop()
  }
}
