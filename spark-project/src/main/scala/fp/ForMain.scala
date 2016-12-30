package fp

/**
  * Created by liguodong on 2016/12/24.
  */
object ForMain extends App{

  for(i <- 1 to 10 if(i%2!=0);j <- 1 to 30 if i==j;z <- 1 to 5){
    println(i+","+j+","+z)
  }

  val v = for (i <- 1 to 10) yield i * 5
  println(v)

  val vv = for(i<-v  if(i%2==0)) yield i
  println(vv)

  for(i<-0 until vv.length) println(i)

}
