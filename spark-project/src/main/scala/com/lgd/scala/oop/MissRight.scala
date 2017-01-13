package com.lgd.scala.oop

import scala.io.Source

/**
  * Created by liguodong on 2016/12/29.
  */
class MissRight {

  try {
    val contents = Source.fromFile("D:/test.txt").mkString
    println(contents)
  } catch {
    case e:Exception => e.printStackTrace()
  }finally {
    println("finally")
  }

  def sayHi = println("Hi...")

  println("name")



}

object MissRight{
  def main(args: Array[String]): Unit = {
    val mr = new MissRight
  }
}