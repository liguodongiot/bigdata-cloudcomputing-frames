package com.lgd.spark.core

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by liguodong on 2017/3/20.
  */
object DatasetsMain {

  def init() ={
    val conf = new SparkConf().setAppName("DatasetsMain").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    (sc,sqlContext)
  }

  def main(args: Array[String]): Unit = {

    val (sc,sqlContext) = init()
    System.setProperty("user.name","liguodong")
    //文本
    //readText(sc,sqlContext)
    //Json格式
    readJson(sc,sqlContext)
  }

  def readText(sc:SparkContext,sqlContext:SQLContext): Unit ={
    // this is used to implicitly convert an RDD to a DataFrame.
    import sqlContext.implicits._

    //    //创建DataSet
    //val rdd = sc.parallelize(Seq(Data(1, "one"), Data(2, "two")))
    //val ds1 = rdd.toDS()
    val ds1 = Seq(Data(1, "one"), Data(2, "two")).toDS()

    ds1.collect()
    ds1.show()

    //创建DataSet
    val df = sqlContext.read.json(sc.parallelize("""{"zip": 94709, "name": "Michael"}""" :: Nil))
    df.as[Person].collect()
    df.as[Person].show()


    //DataSet的WordCount
    import org.apache.spark.sql.functions._
    val ds2 = sqlContext.read.text("D:\\word.txt").as[String]
    val result = ds2.flatMap(_.split(" "))
      .filter(_ != "")
      .toDF()
      .groupBy($"value")
      .agg(count("*") as "numOccurances")
      .orderBy($"numOccurances" desc)
    result.show()
    //val wordCount = ds2.flatMap(_.split(" ")).filter(_ != "").groupBy(_.toLowerCase()).count()


    //创建DataSet
    val lines = sqlContext.read.text("D:\\word.txt").as[String]
    //对DataSet进行操作
    val words = lines.flatMap(_.split(" ")).filter(_ != "")
    //查看DataSet中的内容
    words.collect
    words.show
  }

  def readJson(sc:SparkContext,sqlContext:SQLContext): Unit ={
    //{"name": "UC Berkeley", "yearFounded": 1868, "numStudents": 37581}
    //{"name": "MIT", "yearFounded": 1860, "numStudents": 11318}

    import sqlContext.implicits._
    //创建DataSet
    val schools = sqlContext.read.json("D:/schools.json").as[University]

    //操作DataSet
    schools.map(sc => s"${sc.name} is ${2015 - sc.yearFounded} years old").show

    //{"name": "koko", "age": 15}
    //{"name": "jason", "age": 32}

    //JSON -> DataFrame
    val df1 = sqlContext.read.json("D:/person.json")

    df1.where($"age" >= 20).show
    //df1.where(col("age") >= 20).show
    df1.printSchema

    //DataFrame -> Dataset
    val ds1 = df1.as[People]
    ds1.filter($"age" >= 20).show

    // Dataset -> DataFrame
    val df2 = ds1.toDF

    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.types._

    df2.where($"age" > 0).groupBy((($"age" / 10) cast IntegerType) * 10 as "decade").agg(count("*")).orderBy($"decade").show

    ds1.filter($"age" > 0).groupBy((($"age" / 10) cast IntegerType) * 10).agg(count("name") as "value").toDF().withColumnRenamed("value", "decade").orderBy("decade") .show


//    {"name": "koko", "age": 15,"major": "Math"}
//    {"name": "wiwi", "age": 32,"major": "English"}
//    {"name": "jason", "age": 32,"major": "CS"}
    val df = sqlContext.read.json("D:/student.json")

    val studentDS = df.as[Student]
    println(studentDS.select($"name".as[String], $"age".as[Long]).filter(_._2 > 19).collect().toBuffer)

    println(studentDS.groupBy($"major").count().collect().toBuffer)

    import org.apache.spark.sql.functions._
    println(studentDS.groupBy($"major").agg(avg($"age").as[Double]).collect().toBuffer)

    val majors = Seq(Major("CS", "Computer Science"), Major("Math", "Mathematics")).toDS()

    val joined = studentDS.joinWith(majors, $"major" === $"shortName")
    joined.map(s => (s._1.name, s._2.fullName)).show()
    joined.explain()

  }

}

case class Data(a: Int, b: String)
case class Person( name: String,zip: Long)
case class University(name: String, numStudents: Long, yearFounded: Long)
case class People(age: Long, name: String)
case class Student(name: String, age: Long, major: String)
case class Major(shortName: String, fullName: String)