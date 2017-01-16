package com.lgd.spark.sql

import org.apache.spark.sql.SparkSession
/**
  * Spark2.0提供的SparkSession
  *
  * Created by liguodong on 2017/1/16.
  */

object SparkSessionDemo {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val sparkSession = SparkSession
      .builder()
      .master("local")
      .appName("SparkSessionDemo")
      .getOrCreate()

    System.setProperty("user.name","liguodong")

    // For implicit conversions like converting RDDs to DataFrames
    import sparkSession.implicits._

    //DataSet
    val ds = sparkSession.read.textFile("hdfs://ubuntu:8020/input/person.txt").map(_.split(","))

    val personDs = ds.map(x=>Person(x(0).toLong,x(1),x(2).toInt))

    val personDf = personDs.toDF

    personDf.createOrReplaceTempView("t_person")

    sparkSession.sql("select * from t_person where age >= 20 order by age desc limit 2").show
    sparkSession.sql("desc t_person").show
    sparkSession.stop()

  }
}
