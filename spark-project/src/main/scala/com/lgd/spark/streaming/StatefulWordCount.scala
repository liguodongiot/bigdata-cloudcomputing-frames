package com.lgd.spark.streaming

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by liguodong on 2017/2/4.
  */
object StatefulWordCount {

  /**
    * String 单词
    * Seq[Int] 代表当前这个批次某个单词的结果
    * Option[Int] 代表以前的历史结果
    */
  //(hello,1),(tom,1),(hello,1)
  //(hello,Seq(1,1)),(tom,Seq(1))
  val updateFunc = (iter:Iterator[(String,Seq[Int],Option[Int])]) =>{
    //iter.flatMap(it => Some(it._2.sum+it._3.getOrElse(0)).map(x=>(it._1,x)))
    iter.flatMap { case (x,y,z) => Some(y.sum + z.getOrElse(0)).map(m=>(x,m)) }

    //iter.map(it =>(it._1,it._2.sum+it._3.getOrElse(0)))
    //iter.map{ case (x,y,z) =>(x,y.sum + z.getOrElse(0)) }
  }


  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(5))

    //接收数据
    val ds = ssc.socketTextStream("192.168.133.252",8888)

    //使用updateStateByKey必须设置checkpoint, 实时统计为了避免出现问题
    sc.setCheckpointDir("E:\\dir") //如果在集群里面运行，设置成hdfs路径。

    val result = ds.flatMap(_.split(" ")).map((_,1)).updateStateByKey(updateFunc,new HashPartitioner(sc.defaultParallelism),true)

    //打印结果
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
