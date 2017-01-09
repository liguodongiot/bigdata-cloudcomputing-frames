package com.lgd.spark.core

/**
  * Created by liguodong on 2017/1/9.
  */
object Main {

  def main(args: Array[String]): Unit = {

    val arr = Array(1,2,3,4,5)
    println(arr.max)
    //调用scala的max
    println(arr.reduce(math.max(_,_)))

    //调用java的max
    println(arr.reduce(Math.max(_,_)))


  }

}
