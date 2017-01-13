package com.lgd.spark.core

/**
  * Created by liguodong on 2017/1/13.
  */
object HashTests {

  def main(args: Array[String]): Unit = {

    val key = "net.wintfru.cn"
    val mod = 3

    val rawMod = key.hashCode % mod
    val partNum = rawMod + (if(rawMod <0 ) mod else 0)

    println(partNum)

  }

}
