package implicitconver

import java.io.File

import scala.io.Source

import MyPredef._

/**
  * Created by liguodong on 2017/1/5.
  */
class RichFile(val f:File) {
  def read() = Source.fromFile(f).mkString
}


object RichFile {
  def main(args: Array[String]): Unit = {
    val f = new File("d://word.txt")
    //装饰 ，显示的增强
    //val contents = new RichFile(f).read()

    val contents = f.read()
    println(contents)
  }
}