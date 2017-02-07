package com.lgd.spark.streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by liguodong on 2017/2/6.
  */
object KafkaWordCount {

  val updateFunc = (iter:Iterator[(String,Seq[Int],Option[Int])]) =>{
    iter.flatMap { case (x,y,z) => Some(y.sum + z.getOrElse(0)).map(m=>(x,m)) }
  }
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    //多个zk地址用逗号(,)隔开，多个topic地址用逗号(,)隔开
    val params = Array("192.168.133.252:2181","testGroup1","test","2")
    val Array(zkQuorum,group,topics,numThreads) = params

    val conf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))

    ssc.checkpoint("E:\\dir") //如果在集群里面运行，设置成hdfs路径。

    //receiver可以从一个topic里面读，亦可以从多个topic里面读（注：这里的topic之间类型最好一致，方便处理。）
    //一个topic可以多个receiver读,一个receiver可以有多个线程
    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap

    /**
      * group 消费组
      */
    val data = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)

    data.print()

    //写入kafka是key-value形式，拿出value
    val words = data.map(_._2).flatMap(_.split(" "))

    val wordCount = words.map((_,1)).updateStateByKey(updateFunc,new HashPartitioner(ssc.sparkContext.defaultParallelism),true)

    //将数据存入redis
    /*wordCount.mapPartitions(it =>{
      //jedis连接
      it.toList.toIterator
    })*/

    //打印结果
    wordCount.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
