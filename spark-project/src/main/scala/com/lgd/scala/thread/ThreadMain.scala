package com.lgd.scala.thread

import java.util.concurrent.{Callable, ExecutorService, Executors, Future}

import scala.util.control.Breaks

/**
  * 多线程
  * Created by liguodong on 2017/1/2.
  */
object ThreadMain {

  def main(args: Array[String]): Unit = {

    val threadPool: ExecutorService = Executors.newFixedThreadPool(5);

//    for(i <- 1 to 10){
//      threadPool.execute(new DemoRunnable)
//    }
//    threadPool.shutdown()


    val f:Future[Int] = threadPool.submit(new DemoCallable)

    var i=0

    val loop = new Breaks
    loop.breakable{

      while(!f.isDone){
        println(s"等待了 $i 秒。")
        i += 1
        Thread.sleep(1000)
        if(i>10){
          println("等待超时。。。")
          loop.break
        }
      }
    }

    if(f.isDone){
      println("结果为"+f.get())
    }

    threadPool.shutdown()
  }
}

class DemoRunnable extends Runnable{

  override def run(): Unit = {
    println(Thread.currentThread().getName)
    Thread.sleep(1000)
  }

}

class DemoCallable extends Callable[Int]{
  override def call(): Int = {
    Thread.sleep(5000)
    100
  }
}

