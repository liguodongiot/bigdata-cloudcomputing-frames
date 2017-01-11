package com.lgd.spark.core

import java.net.URL

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/11.
  */
object AdvUrlCount {

  def main(args: Array[String]): Unit = {

    //从数据库中加载字典表
    val arr = Array("java.wintfru.cn","php.wintfru.cn","net.wintfru.cn")

    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("UrlCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //对数据进行切分，结果为(url,1)
    val rddUrlAndOne = sc.textFile("E:/data/website.log").map(line=>{
      val f = line.split("\t")
      (f(1),1)
    })

    val rddByKeyUnion = rddUrlAndOne.reduceByKey(_+_)
    println(rddByKeyUnion.collect().toBuffer)

    val rddHost = rddByKeyUnion.map(t=>{
      val (host,url,times) = (new URL(t._1).getHost,t._1,t._2)
      (host,url,times)
    })
    println(rddHost.collect().toBuffer)

//    val rddJava = rddHost.filter(_._1=="java.wintfru.cn")
//    val sortJava = rddJava.sortBy(_._3,false).take(3)
//    println(sortJava.toBuffer)

    for( ins <- arr){
      val rdd = rddHost.filter(_._1==ins)
      //SparkRDD的排序
      val sort = rdd.sortBy(_._3,false).take(3)
      //通过JDBC向数据库中存储数据
      //id 学院 url 次数 访问日期
      println(sort.toBuffer)
    }
    sc.stop()
  }

}
