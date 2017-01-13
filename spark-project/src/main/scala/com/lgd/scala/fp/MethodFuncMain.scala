package com.lgd.scala.fp

/**
  * Created by liguodong on 2016/12/24.
  */
object MethodFuncMain extends App{

  //函数
  val f1 = (x:Int,y:Int) => x+y

  val f2:Int=>String = { x => x.toString}

  val f3:(Int,Int)=> String = { (x,y) => (x+y).toString}

  println(f1(2,3))
  println(f2(2))
  println(f3(2,3))


  //复习函数和方法
  def test(a:Int,b:String) : Int ={
    a+b.toInt
  }

  val f11 = (a:Int,b:Int) => {a+b}

  val f22:(Int,Int)=>Int = {(a,b)=>
    val c = a+b
    c+1
  }
  println(f11(2,3)+", "+f22(2,2)+", "+test(1,"100"))


  val func1 = (x:Int,y:Double) => (y,x)
  println(func1(2,3.1))

  val func2:(Int,Double)=>(Double,Int) = { (a,b)=>
    (b,a)
  }
  println(func1(2,3.1)+" "+func2(2,5.1))

}
