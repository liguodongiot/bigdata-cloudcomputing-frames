package com.lgd.scala.oop

/**
  * 私有构造方法：只能在伴生对象可用
  *
  * Created by liguodong on 2016/12/29.
  */
class Person {
  val id = 9578    //getId
  var name = "李四"  //getName setName

  //类私有字段,只能在类的内部使用
  private var gender: String = "meal"

  def printGender = gender
  def setGender(ge:String):String = {
    gender=ge
    gender
  }

  //对象私有字段,访问权限更加严格的，Person类的方法只能访问到当前对象的字段
  private[this] val pet = "小强"

  def printPet = println(pet)

}

//伴生对象
object Person{

  def main(args: Array[String]): Unit = {
    val p = new Person
    println(p.id + " " + p.name)

    p.name = "奥巴马"

    println(p.name)

    println(p.gender)
  }

}


object impala.Demo {

  def main(args: Array[String]): Unit = {
    val p = new Person
    println(p.id + " " + p.name)

    p.name = "奥巴马"
    println(p.name)
    println(p.printGender)
    println(p.setGender("df"))
    p.printPet
  }

}
