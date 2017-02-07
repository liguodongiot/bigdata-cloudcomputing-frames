package com.lgd.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}

/**
  * Created by liguodong on 2017/2/7.
  */
object WindowOpts {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("WindowOpts").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Milliseconds(5000))
    val lines = ssc.socketTextStream("192.168.133.252",9999)
    val pairs = lines.flatMap(_.split(" ")).map((_,1))


    /*
      window length - The duration of the window.
      sliding interval - The interval at which the window operation is performed.
      These two parameters must be multiples（倍数） of the batch interval of the source DStream.
     */
    //Seconds(15) 窗口时间
    //Seconds(10) 滑动时间间隔
    val windowWordCounts = pairs.reduceByKeyAndWindow((a:Int,b:Int)=>(a+b),Seconds(15),Seconds(10))

    //windowWordCounts.print()

    //Map((hello,5),(jerry,2),(ketty,3))
    val a = windowWordCounts.map(_._2).reduce(_+_)
//    a.foreachRDD(rdd=>{
//      println(rdd.take(0))
//    })
    a.print()



    ssc.start()
    ssc.awaitTermination()
  }


}
