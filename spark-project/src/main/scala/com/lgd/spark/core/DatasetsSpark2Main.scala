package com.lgd.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by liguodong on 2017/3/20.
  */
object DatasetsSpark2Main {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local")
      .appName("Word Count")
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._
    val result = spark.read.textFile("D:\\word.txt").flatMap(_.split(" "))
      .groupBy($"value" as "word")
      .agg(count("*") as "counts")
      .orderBy($"counts" desc)

    result.show()

    spark.stop()
  }
}
