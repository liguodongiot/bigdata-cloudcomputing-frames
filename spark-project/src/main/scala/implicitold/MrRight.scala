package implicitold

/**
  * Created by liguodong on 2017/1/6.
  */
class MrRight[T] {
  def choose[T <: Comparable[T]](first:T,second:T):T = {
    if(first.compareTo(second)>0) first else second
  }
}

object MrRight{

  def main(args: Array[String]): Unit = {
    val mr = new MrRight[Boy]
    val b1 = new Boy("zhangsan", 99)
    val b2 = new Boy("lisi", 100)
    val b = mr.choose(b1, b2)
    println(b.name)
  }

}
