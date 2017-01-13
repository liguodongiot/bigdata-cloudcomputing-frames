package com.lgd.scala.fp

/**
  * Created by liguodong on 2016/12/26.
  */
object TupleMain extends App{

  val t,(a,b,c) = (1,"fgd",323.23)

  println(a)
  println(t._2)

  val tt = (1,"dfds",32)
  println(tt._3)

}
