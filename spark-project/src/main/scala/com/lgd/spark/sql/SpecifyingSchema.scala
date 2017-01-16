package com.lgd.spark.sql

import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/1/16.
  */
object SpecifyingSchema {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")
    System.setProperty("user.name","liguodong")

    //创建SparkConf()并设置App名称
    val conf = new SparkConf().setAppName("SpecifyingSchema").setMaster("local")
    //SQLContext要依赖SparkContext
    val sc = new SparkContext(conf)
    //创建SQLContext
    val sqlContext = new SQLContext(sc)

    //从指定的地址创建RDD
    val personRDD = sc.textFile("hdfs://ubuntu:8020/input/person.txt").map(_.split(","))

    //通过StructType直接指定每个字段的schema
    val schema = StructType(
      List(
        StructField("id", IntegerType, true),
        StructField("name", StringType, true),
        StructField("age", IntegerType, true)
      )
    )
    //将RDD映射到rowRDD
    val rowRDD = personRDD.map(p => Row(p(0).toInt, p(1).trim, p(2).toInt))

    //将schema信息应用到rowRDD上
    val personDataFrame = sqlContext.createDataFrame(rowRDD, schema)

    //注册表
    personDataFrame.registerTempTable("t_person")

    //执行SQL
    val df = sqlContext.sql("select * from t_person order by age desc limit 4")

    //将结果以JSON的方式存储到指定位置
    //df.write.json("hdfs://ubuntu:8020/outputJson/")
    df.write.mode("overwrite").json("hdfs://ubuntu:8020/outputJson/")

    //覆盖
    import org.apache.spark.sql.SaveMode._
    df.write.mode(Overwrite).save("hdfs://ubuntu:8020/outputSave/")

    //停止Spark Context
    sc.stop()
  }

}
