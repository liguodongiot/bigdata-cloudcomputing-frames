package com.lgd.spark.core

import java.io.{BufferedReader, FileInputStream, InputStreamReader}

import scala.collection.Iterator
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by liguodong on 2017/1/13.
  */
object IpDemo {

  def ip2Long(ip:String):Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for(i <- 0 until fragments.length){
      //每一位进行 或操作  将ipNum右移8位 相当于乘256
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

//  def readData(path: String) = {
//    val br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))
//    var s: String = null
//    var flag = true
//    val lines = new ArrayBuffer[String]()
//    while (flag){
//      s = br.readLine()
//      if (s != null)
//        lines += s
//      else
//        flag = false
//    }
//    lines
//  }
  def readData(path: String) = {
    val source = Source.fromFile(path)
    val iterator:Iterator[String] = source.getLines
    val lines = new ArrayBuffer[String]()
    for (it <- iterator){
      lines += it
    }
    lines
  }

  def binarySearch(lines: ArrayBuffer[String], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle).split("\\|")(2).toLong) && (ip <= lines(middle).split("\\|")(3).toLong))
        return middle
      if (ip < lines(middle).split("\\|")(2).toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

  def main(args: Array[String]): Unit = {
    val ipStr = "118.144.130.10"
    val ipLong = ip2Long(ipStr)
    println(ipLong)

    val lines = readData("e:/data/ip.txt")
    val index = binarySearch(lines, ipLong)
    print(lines(index))

  }

}
