package com.lgd.spark.sql

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

/**
  * Created by liguodong on 2017/1/17.
  */
object HiveOnSparkDemo {
  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")
    //System.setProperty("user.name","liguodong")

    //远程调试
    val conf = new SparkConf().setAppName("HiveOnSparkDemo")
     // .setMaster("spark://192.168.133.252:7077")
      //.set("spark.sql.warehouse.dir","hdfs://ubuntu:8020/user/hive/warehouse")
     // .setJars(List("E:\\study\\sourcecode\\bigdata-cloudcomputing-frames\\spark-project\\target\\original-spark-1.0.jar"))

    val sc = new SparkContext(conf)
    //val sqlContext = new SQLContext(sc)

    val hiveContext = new HiveContext(sc)

    hiveContext.sql("use default")
    hiveContext.sql("select * from Person").show
    hiveContext.sql("desc Person").show

    sc.stop()
  }
}
