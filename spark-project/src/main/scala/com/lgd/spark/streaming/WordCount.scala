package com.lgd.spark.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/2/4.
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //设置日志级别
    //sc.setLogLevel("WARN")
    //sc.setLogLevel("DEBUG")
    //sc.setLogLevel("ERROR")
    //sc.setLogLevel("INFO")

    val ssc = new StreamingContext(sc,Seconds(5))

    //接收数据
    val ds = ssc.socketTextStream("192.168.133.252",8888)

    //DStream是一个特殊的RDD
    //hello kaka hello oppo
    val result = ds.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    //打印结果
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
