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
    val list = List(("e", 5), ("c", 3), ("d", 4), ("c", 2), ("a", 1))

    list.filter(x =>x._1>="b" &&  x._1<="d").foreach(x=>println(x._1,x._2))


    //val list2 = List(("a", "1 2"), ("b", "3 4"))
    //list2.map(_._2.split(" "))
    val str = "one_two"

    val (one,two) = (str.split("_")(0),str.split("_")(1))
    println(s"$one,$two")
  }

}
