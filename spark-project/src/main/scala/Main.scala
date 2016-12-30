/**
  * Created by liguodong on 2016/12/24.
  */
object Main extends App{
  println("hello,world...")

  val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
  println(lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2)))
  lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).map(t=>(t._1,t._2.size))
  lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))




}
