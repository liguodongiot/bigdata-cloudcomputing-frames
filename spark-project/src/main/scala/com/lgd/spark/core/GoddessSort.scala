package com.lgd.spark.core

import org.apache.spark.{SparkConf, SparkContext}


object ContextOrder{
  implicit object GoddessOrdering extends Ordering[Goddess]{
    override def compare(x: Goddess, y: Goddess): Int = {
      if(x.faceValue>y.faceValue){
        1
      }else if(x.faceValue==y.faceValue){
        if(x.age>=y.age) -1 else 1
      }else{
        -1
      }
    }
  }
}

/**
  * 排序规则
  * 先按颜值排序，后按年龄排序
  * name,faveValue,age
  *
  * Created by liguodong on 2017/1/13.
  */
object GoddessSort {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "E:\\install\\hadoop-2.6.0")

    val conf = new SparkConf().setAppName("GoddessSort").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rddInit = sc.parallelize(List(("Yui hatano",99,20),("Tylar swift",88,23),("Lady gaga",88,30)))

    //方式一
    //false表示降序
    //val rddSort = rddInit.sortBy(x => Goddess(x._1,x._2,x._3),false)
    //println(rddSort.collect().toBuffer)

    //方式二
    import ContextOrder._
    val rddSort = rddInit.sortBy(x => Goddess(x._1,x._2,x._3),false)
    println(rddSort.collect().toBuffer)

    sc.stop()
  }

}


//方式一
//样例类特点：不用new，就能创建实例,可以进行模式匹配
//样例类可以不用val或var修饰，是多余的。

//自定义比较规则
//case class Goddess(val name:String,val faceValue:Int,val age:Int) extends Ordered[Goddess] with Serializable{
//  override def compare(that: Goddess): Int = {
//    if(this.faceValue==that.faceValue){
//      -(this.age-that.age)
//    }else{
//      this.faceValue-that.faceValue
//    }
//  }
//}


//方式二 隐式转换
case class Goddess(name:String,faceValue:Int,age:Int) extends Serializable
