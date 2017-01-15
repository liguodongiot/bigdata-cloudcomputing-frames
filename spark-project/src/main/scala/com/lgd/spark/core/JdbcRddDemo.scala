package com.lgd.spark.core

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/14.
  */
object JdbcRddDemo {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val connection = () => {
      Class.forName("com.mysql.jdbc.Driver").newInstance()
      DriverManager.getConnection("jdbc:mysql://localhost:3306/liguodong", "root", "liguodong")
    }
    val jdbcRDD = new JdbcRDD(
      sc,
      connection,
      "SELECT * FROM ip_location_info where id >= ? AND id <= ?",
      1,
      4,
      2, //分区数
      r => {
        val id = r.getInt(1)
        val location = r.getString(3)
        (id, location)
      }
    )
    println(jdbcRDD.collect().toBuffer)
    sc.stop()
  }
}
