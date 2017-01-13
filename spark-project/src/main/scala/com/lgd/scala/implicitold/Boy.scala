package com.lgd.scala.implicitold

/**
  * Created by liguodong on 2017/1/6.
  */
class Boy(val name:String,var faceValue:Int) extends Comparable[Boy]{
  override def compareTo(o: Boy): Int =  this.faceValue - o.faceValue
}
