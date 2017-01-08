package implicitconver

/**
  * Created by liguodong on 2017/1/5.
  */

//视图界定
//必须传进去一个隐式转换的函数
//class Chooser[T <% Ordered[T]] {
//  def choose(first:T,second:T): T ={
//    if(first > second) first else second
//  }
//}


//上下文界定
//必须传进去一个隐式转换的值
class Chooser[T : Ordering] {
  def choose(first:T,second:T): T ={
    val ord = implicitly[Ordering[T]]
    if(ord.gt(first,second)) first else second
  }
}

object Chooser{
  def main(args: Array[String]): Unit = {

    //通过隐式转换没有侵入到Girl类里面
    import MyPredef._
    val c = new Chooser[Girl]
    val g1 = new Girl("baby",99)
    val g2 = new Girl("akka",999)
    val g = c.choose(g1,g2)
    println(g.name)

  }
}
