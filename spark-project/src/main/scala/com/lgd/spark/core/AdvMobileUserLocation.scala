package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/11.
  */
object AdvMobileUserLocation {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("AdvMobileUserLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //读取基站信息
    val rddMobileNumberAndTime = sc.textFile("E://data//bs").map(line => {
      val fields = line.split(",")
      //（手机号，基站ID，时间，事件类型）
      val (mobileNum,dateTime,baseStation,eventType) = (fields(0),fields(1),fields(2),fields(3))
      val timeLong = if(eventType == 1) -dateTime.toLong else dateTime.toLong
      ((mobileNum,baseStation),timeLong)
    })
    println(rddMobileNumberAndTime.collect().toBuffer)

    //(CC0710CC94ECC657A8561DE549D940E0,(18688888888,80641308505700))
    val rddBsTime = rddMobileNumberAndTime.reduceByKey(_+_).map(t=>{
      val (baseStation,mobileNum,timeLong) = (t._1._2,t._1._1,t._2)
      (baseStation,(mobileNum,timeLong))
    })
    println(rddBsTime.collect().toBuffer)


    val rddBsLoc = sc.textFile("E:/data/loc/loc_info.txt").map(line=>{
      val f = line.split(",")
      //基站ID，（经度，纬度）
      (f(0),(f(1),f(2)))
    })

    //根据基站ID进行join
    //(CC0710CC94ECC657A8561DE549D940E0,((18688888888,80641308505700),(116.303955,40.041935)))
    val rddJoin = rddBsTime.join(rddBsLoc)
    println(rddJoin.collect().toBuffer)

    val rddMoveLoc = rddJoin.map(t =>{
      val (locNo,mobileNo,longTime,x,y) = (t._1,t._2._1._1,t._2._1._2,t._2._2._1,t._2._2._2)
      (mobileNo,locNo,longTime,x,y)
    })

    //根据手机号分组
    val rddByMobile = rddMoveLoc.groupBy(_._1)

    //根据时间排序保存前两个
    val rddBySort = rddByMobile.mapValues(it=>{
      it.toList.sortBy(_._3).reverse.take(2)
    })
    println(rddBySort.collect().toBuffer)
    //保存到某个目录
    rddBySort.saveAsTextFile("E:/data/out")

  }


}
