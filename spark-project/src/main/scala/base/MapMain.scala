package base

/**
  * Created by liguodong on 2016/12/26.
  */
object MapMain extends App{

  import scala.collection.mutable._
  var map = Map((11,12),(12,3))

  map += ((1,2),(2,3))

  val pair = (22,3)
  map += pair

  println(map)

}
