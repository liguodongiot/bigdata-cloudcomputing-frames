package com.lgd.spark.streaming

import java.net.InetSocketAddress

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by liguodong on 2017/2/5.
  */
object FlumePollWordCount {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("FlumePollWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))

    //从Flume中拉取数据(这里的IP和PORT是flume的IP和端口号)
    //这里可以传入多个flume的地址
    val address = Seq(new InetSocketAddress("192.168.133.252",8888))
    val flumeStream = FlumeUtils.createPollingStream(ssc,address,StorageLevel.MEMORY_AND_DISK)
    val words = flumeStream.flatMap(x => new String(x.event.getBody().array()).split(" ")).map((_,1))
    val result = words.reduceByKey(_+_)
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
