package com.lgd.spark.quota

import kafka.serializer.StringDecoder
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/2/26.
  */
object BadUserFilter {

  def main(args: Array[String]): Unit = {

    val Array(zkQuorum, group, topics, numThreads) = Array("192.168.133.252:2181", "testGroup", "access", "2")

    val dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")

    val conf = new SparkConf().setAppName("BadUserFilter").setMaster("local[4]")
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Milliseconds(10000))
    sc.setCheckpointDir("E:\\dir")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> zkQuorum,
      "group.id" -> group,
      "auto.offset.reset" -> "smallest"
    )

    val dstream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc,
      kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK_SER)

    //取出所有的value
    val lines = dstream.map(_._2)
    val splitedLines = lines.map(_.split("\t"))
    val filteredLines = splitedLines.filter(f => {
      //事件类型
      val et = f(3)
      //物品
      val item = f(8)
      et == "11" && item == "生命药水"
    })

    //用户，使用物品时间
    val grouedWindow: DStream[(String, Iterable[Long])] = filteredLines.map(f => (f(7), dateFormat.parse(f(12)).getTime)).groupByKeyAndWindow(Milliseconds(300000), Milliseconds(200000))
    val filtered: DStream[(String, Iterable[Long])] = grouedWindow.filter(_._2.size >= 5)

    val itemAvgTime = filtered.mapValues(it => {
      val list = it.toList.sorted
      //使用药水次数
      val size = list.size
      //计算第一次和最后一次使用药水时间间隔
      val first = list(0)
      val last = list(size - 1)
      val cha: Double = last - first
      cha / size
    })

    val badUser: DStream[(String, Double)] = itemAvgTime.filter(_._2 < 10000)

    badUser.foreachRDD(rdd => {
      rdd.foreachPartition(it => {
        val conn = JedisConnPool.getConn()
        it.foreach(t => {
          val user = t._1
          val avgTime = t._2
          val currentTime = System.currentTimeMillis()
          conn.set(user + "_" + currentTime, avgTime.toString)
        })
        conn.close()
      })
    })
    filteredLines.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
