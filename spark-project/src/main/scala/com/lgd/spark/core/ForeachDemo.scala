package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/10.
  */
object ForeachDemo {
  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    //本地方式
    //2表示启动两个线程
    //val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local[2]")
    //默认一个线程
    val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9),3)
    //rdd1.foreach(println(_))
    rdd1.foreachPartition(x => println(x.reduce(_ + _)))
    sc.stop()

  }
}
