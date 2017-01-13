package com.lgd.scala.implicitconver

/**
  * Created by liguodong on 2017/1/5.
  */
class Pair[T <: Comparable[T]] {

  def bigger(first:T,second:T) = {
    if(first.compareTo(second)>0) first else second
  }

}

object Pair{
  def main(args: Array[String]): Unit = {
    val p = new Pair[String]()
    println(p.bigger("Storm","Spark"))

    val p2 = new Pair[Integer]()
    println(p2.bigger(1,2))

  }
}

