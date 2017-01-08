package implicitconver

/**
  * 定义比较规则侵入了Boy类里面，如果要定义其他比较规则必须重写方法
  * Created by liguodong on 2017/1/5.
  */
class Boy(val name:String,var faceValue:Int) extends Comparable[Boy]{
  override def compareTo(o: Boy): Int =  this.faceValue - o.faceValue
}

object Boy{
  def main(args: Array[String]): Unit = {
    val b1 = new Boy("laoxiao",99)
    val b2 = new Boy("dsf",999)

    val arr = Array[Boy](b1,b2)
    val sortArr = arr.sortBy(x=>x).reverse
    for (b <- sortArr){
      println(b.name)
    }
  }
}