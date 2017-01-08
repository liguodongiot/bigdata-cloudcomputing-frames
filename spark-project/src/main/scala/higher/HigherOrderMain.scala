package higher

/**
  * Created by liguodong on 2017/1/4.
  */
object HigherOrderMain extends App {

  def doubleX(x:Int):Int = x*2

  val arr = Array(1,2,3,4,5)
  //方法转为函数

  arr.map(doubleX).foreach(println)

  arr.map(doubleX _).foreach(println)

  arr.map(doubleX(_)).foreach(println)

  val func:(Int,Int)=>Int = {
    (x,y)=> x+y
  }

  val func2 = (x:Int,y:Int) => x+y

  println("------------------")

  println(arr.map(x=>func(x,x)).toBuffer)

  def muti(x:Int):Int = x*x
  println(arr.map(muti).toBuffer)

  //柯里化
  //第一种
  def mm1(x:Int)(y:Int) = x*y

  //第二种
  def mm2(x:Int) = (y:Int) => x*y

  def curlyMethod2() = (x:Int) =>{ x*x }

  def curlyMethod = (x:Int) =>{ x*x }

  println(arr.map(curlyMethod).toBuffer)


  def xx(x:Int)(y:Int)(z:Int) =  x * (y + z)

  println(xx(2)(2)(3))

  val testXx = xx(5) _
  println(testXx(2)(3))


}
