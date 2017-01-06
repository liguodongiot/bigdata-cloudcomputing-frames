package implicitconver



/**
  * Created by liguodong on 2017/1/5.
  */
//所有的隐式值和隐式方法必须放到object
object Context{
  implicit val aaaaa = "liyannan"
  implicit val i = 1
}


object ImplicitValue {

  def sayHi()(implicit name:String = "liguodong"): Unit ={
    println(s"hello,$name.")
  }

  def main(args: Array[String]): Unit = {

    sayHi()

    import Context._
    sayHi()

    sayHi()("likexin")

  }

}
