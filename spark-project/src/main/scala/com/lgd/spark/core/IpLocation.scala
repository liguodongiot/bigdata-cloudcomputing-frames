package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/13.
  */
object IpLocation {

  def ip2Long(ip:String):Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for(i <- 0 until fragments.length){
      //每一位进行 或操作  将ipNum右移8位 相当于乘256
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def binarySearch(lines: Array[(Long,Long,String)], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle)._1)  && (ip <= lines(middle)._2) )
        return middle
      if (ip < lines(middle)._1)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("IpLocation").setMaster("local[2]")
    val sc = new SparkContext(conf)

    //ip规则
    //最小IP数值，最大IP数值，省份
    val rddInit = sc.textFile("E:/data/ip.txt").map(lines =>{
      val field = lines.split("\\|")
      val (startNum,endNum,province) = (field(2).toLong,field(3).toLong,field(6))
      (startNum,endNum,province)
    })

    //Driver端
    //全部的IP映射规则
    val rddAll = rddInit.collect()

    //广播规则
    val rddBroadcast = sc.broadcast(rddAll)

    //加载要处理的数据
    val rddIps = sc.textFile("E:/data/http.format").map(line=>{
      val fields = line.split("\\|")
      fields(1)
    })

    val rddResult = rddIps.map(ip=>{
      val ipNum = ip2Long(ip)
      val index = binarySearch(rddBroadcast.value,ipNum)
      val info = rddBroadcast.value(index)
      info
    })
    println(rddResult.collect().toBuffer)

    sc.stop()
  }


}
