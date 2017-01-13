package com.lgd.scala.implicitold

/**
  * Created by liguodong on 2017/1/6.
  */
//类没有指定是视图界定还是上下文界定
class MissRight[T] {

  //柯里化  隐式转换函数  上下文查找
  //相当于View Bound
  def choose(first: T, second: T)(implicit ord : T => Ordered[T]): T = {
    if(first > second) first else second
  }

  //相当于Context Bound
  def select(first: T, second: T)(implicit ord : Ordering[T]): T ={
    if(ord.gt(first, second)) first else second
  }

  def random(first: T, second: T)(implicit ord : Ordering[T]): T ={
    import Ordered.orderingToOrdered
    if(first > second) first else second
  }
}

object MissRight {

  def main(args: Array[String]): Unit = {
    val mr = new MissRight[Girl]
    val g1 = new Girl("hatanao", 98, 28)
    val g2 = new Girl("sora", 95, 33)

    import MyPreDef._
    val g = mr.choose(g1, g2)
    //val g = mr.select(g1, g2)
    println(g.name)
  }

}

