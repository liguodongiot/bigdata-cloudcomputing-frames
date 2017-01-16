package com.lgd.spark.sql

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/16.
  */

case class Person(id:Long,name:String,age:Int)

object SqlContextDemo {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("SqlContextDemo").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    System.setProperty("user.name","liguodong")

    val rdd = sc.textFile("hdfs://ubuntu:8020/input/person.txt").map(_.split(","))

    val personRdd = rdd.map(x=>Person(x(0).toLong,x(1),x(2).toInt))

    import sqlContext.implicits._
    val personDf = personRdd.toDF

    personDf.registerTempTable("t_person")

    sqlContext.sql("select * from t_person where age >= 20 order by age desc limit 2").show
    sqlContext.sql("desc t_person").show
    sc.stop()

  }
}
