package com.lgd.scala.oop

/**
  * Created by liguodong on 2016/12/29.
  */
//gender:String private[this]  伴生对象都不能访问
class People(var name:String,val age:Int,gender:String,var weight:Int = 100) {


}

object People {
  def main(args: Array[String]): Unit = {

    val p = new People("lld",23,"femail")
    println(p.weight)


  }
}

