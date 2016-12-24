package base

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
}
