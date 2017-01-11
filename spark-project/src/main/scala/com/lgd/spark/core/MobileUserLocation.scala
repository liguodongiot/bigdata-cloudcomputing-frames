package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/11.
  */
object MobileUserLocation {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("MobileUserLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)
    //读取基站信息
    //sc.textFile("E://data//bs").map(_.split(",")).map(x=>(x(0),x(1),x(2),x(3)))
    val rddMobileNumberAndTime = sc.textFile("E://data//bs").map(line => {
//      val fields = line.split(",")
//      val eventType = fields(3)
//      val dateTime = fields(1)
//      val timeLong = if(eventType == 1) -dateTime.toLong else dateTime.toLong
//      (fields(0)+"_"+fields(2),timeLong)
      //(s"${fields(0)}_${fields(2)}",timeLong)

      val fields = line.split(",")
      //（手机号，基站ID，时间，事件类型）
      val info,(mobileNum,dateTime,baseStation,eventType) = (fields(0),fields(1),fields(2),fields(3))
      val timeLong = if(eventType == 1) -dateTime.toLong else dateTime.toLong
      //(s"$mobileNum-$baseStation",timeLong)
      (s"${info._1}_${info._3}",timeLong)
    })
    println(rddMobileNumberAndTime.collect().toBuffer)

    //手机号在某个基站停留的时间
    //(18688888888-16030401EAFB68F1E3CDF819735E1C66,40320654252400)
    val rddBsTime = rddMobileNumberAndTime.groupBy(_._1).mapValues(_.foldLeft(0L)(_+_._2))
    println(rddBsTime.collect().toBuffer)


    //手机号，基站，停留时间
    val rddNumBsTime = rddBsTime.map( t => {
      //手机号_基站，停留时间
      val (mobileBs,stayTime) = (t._1,t._2)
      //手机号，基站
      val (mobile,laction) = (mobileBs.split("_")(0),mobileBs.split("_")(1))
      (mobile,laction,stayTime)
    })
    //根据手机号分组
    val rddGroupNum = rddNumBsTime.groupBy(_._1)
    println(rddGroupNum.collect().toBuffer)

    //对手机号里面的基站停留时间排序
    val rddSort = rddGroupNum.mapValues(it=>{
      it.toList.sortBy(_._3).reverse.take(2)
    })
    println(rddSort.collect().toBuffer)
    sc.stop()
  }
}
