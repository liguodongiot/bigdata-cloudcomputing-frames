package com.lgd.spark.core


import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/11.
  */
object UrlCount {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //对数据进行切分，结果为(url,1)
    val rddUrlAndOne = sc.textFile("E:/data/website.log").map(line=>{
      val f = line.split("\t")
      (f(1),1)
    })

    val rddHost = rddUrlAndOne.reduceByKey(_+_).map(t=>{
      val (host,url,times) = (new URL(t._1).getHost,t._1,t._2)
      (host,url,times)
    })
    println(rddHost.collect().toBuffer)

    val rddTop3 = rddHost.groupBy(_._1).mapValues(it=>{
      //Scala的排序
      it.toList.sortBy(_._3).reverse.take(3)
    })
    println(rddTop3.collect().toBuffer)

    sc.stop()
  }
}
