package fp

/**
  * Created by liguodong on 2016/12/26.
  */
object ArrayDemo extends App{

  val arr = Array(1,2,5,6,7,3,4)

  println(arr.sorted.reverse.toList)
  println(arr.sortBy(x=>x+1).toList)
  println(arr.sortWith(_>_).toList)
  println(arr.sortWith(_<_).toList)

}
