import java.text.SimpleDateFormat

import org.apache.commons.lang3.time.FastDateFormat
import org.joda.time.DateTime

/**
  * Created by liguodong on 2016/12/24.
  */
object Main extends App{
  println("hello,world...")

  //单词统计
  val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
  println(lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2)))
  lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).map(t=>(t._1,t._2.size))
  lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))

  //时间处理
  val datetime = new DateTime(2016,10,8,0,0,0)
  println(datetime.plusDays(30))
  val dateFormat = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")
  val logTime = dateFormat.parse("2016年2月1日,星期一,10:01:37").getTime
  println(logTime)
  val datelong = new DateTime(logTime)
  println(datelong.toString("yyyyMMdd"))
  println(datelong.toString("yyyy年MM月dd日,E,HH:mm:ss"))

  val simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")
  val logTime2 = simpleDateFormat.parse("2016年2月1日,星期一,10:01:37").getTime
  println(new DateTime(logTime2).toString("yyyy年MM月dd日,E,HH:mm:ss"))
}
