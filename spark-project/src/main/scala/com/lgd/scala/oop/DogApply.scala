package com.lgd.scala.oop

/**
  * Created by liguodong on 2016/12/29.
  */
class DogApply {
}

object DogApply{

  def apply(): DogApply ={
    new DogApply()
  }

  def apply(name:String): Unit ={
    println(name)
  }

}

object Main extends App{
  val dog = DogApply
  //println(dog)

  val dogName = DogApply("oao")
}