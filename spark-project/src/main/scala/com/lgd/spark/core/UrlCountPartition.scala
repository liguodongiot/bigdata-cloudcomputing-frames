package com.lgd.spark.core

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by liguodong on 2017/1/11.
  */
object UrlCountPartition {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("UrlCountPartition").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rddSplit = sc.textFile("E:/data/website.log").map(line =>{
      val f = line.split("\t")
      (f(1),1)
    })

    val rddUnion = rddSplit.reduceByKey(_+_)

    //(java.wintfru.cn,http://java.wintfru.cn/java/course/javaee.shtml,1000)

    //（K，V）类型
    val rddHostUrlCount = rddUnion.map(t =>{
      val (host,url,count) = (new URL(t._1).getHost,t._1,t._2)
      (host,(url,count))
    })

    //val rddHostUrlCount = rddUnion.map(t => (new URL(t._1).getHost,(t._1,t._2)))

    println(rddHostUrlCount.collect().toBuffer)

    //重新分区
    //rddHostUrlCount.repartition(3).saveAsTextFile("E:/data/out3")

    //将host排重
    val hostArr = rddHostUrlCount.map(_._1).distinct().collect()
    println(hostArr.toBuffer)

    //自定义分区
    val hostPartitioner = new HostPartitioner(hostArr)

    //默认是Hash分区
    //对每个分区进行局部排序取出前三
    val rddTop3 = rddHostUrlCount.partitionBy(hostPartitioner).mapPartitions(it=>{
      it.toList.sortBy(_._2._2).reverse.take(3).toIterator
    })

    rddTop3.saveAsTextFile("E:/data/out33")

    //hash分区
    rddHostUrlCount.partitionBy(new HashPartitioner(3)).saveAsTextFile("E:/data/out333")


  }

}

//自定义分区
class HostPartitioner(val hostArr:Array[String]) extends Partitioner{

  val partMap = new mutable.HashMap[String,Int]()
  var count = 0
  for(host <- hostArr){
    partMap += (host -> count)
    count += 1
  }

  override def numPartitions: Int = hostArr.length

  override def getPartition(key: Any): Int = {
    partMap.getOrElse(key.toString,0)
  }

}