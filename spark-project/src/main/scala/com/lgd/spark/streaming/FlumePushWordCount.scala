package com.lgd.spark.streaming

import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by liguodong on 2017/2/5.
  */
object FlumePushWordCount {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val host = "192.168.133.238" //args(0)
    val port = 8888 //args(1).toInt

    val conf = new SparkConf().setAppName("FlumePushWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))


    //推送方式：flume向spark发送数据
    //这里的IP和PORT是Spark运行Executor端的IP和端口（这种方式的缺点是只能指定一个IP地址，生产环境一般不采用这种方式）
    val flumeStream = FlumeUtils.createStream(ssc,host,port)

    //flume中的数据通过event.getBody()才能拿到真正的内容
    val words = flumeStream.flatMap(x=>new String(x.event.getBody.array()).split(" ")).map((_,1))
    val results = words.reduceByKey(_+_)

    results.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
