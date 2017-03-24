package com.lgd.scala.thread

import java.util.concurrent.{Callable, ExecutorService, Executors, Future}

import scala.util.control.Breaks

/**
  * 多线程
  * Created by liguodong on 2017/1/2.
  */
object ThreadMain {

  def main(args: Array[String]): Unit = {

    val threadPool: ExecutorService = Executors.newFixedThreadPool(5)

    for(i <- 1 to 10){
      //threadPool.execute(new DemoRunnable)
      //threadPool.submit(new DemoThread)
      //接收的参数是Runnable接口的实现而不是Thread,线程池拿到Runnable接口的实现之后，
      //只关心一个run方法，并且也只会调用它。
      //拿到的线程名是线程池生成时已经生成的(newFixedThreadPool)，和submit与execute的线程任务无关。
      //如果真的对线程名有要求，应该在线程内部改，即run方法内部。
      threadPool.execute(new DemoThread)
    }
    threadPool.shutdown()


//    val f:Future[Int] = threadPool.submit(new DemoCallable)
//
//    var i=0
//
//    val loop = new Breaks
//    loop.breakable{
//
//      while(!f.isDone){
//        println(s"等待了 $i 秒。")
//        i += 1
//        Thread.sleep(1000)
//        if(i>10){
//          println("等待超时。。。")
//          loop.break
//        }
//      }
//    }
//
//    if(f.isDone){
//      println("结果为"+f.get())
//    }
//
//    threadPool.shutdown()
  }
}

class DemoRunnable extends Runnable{

  //创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。

  override def run(): Unit = {
    //设置线程名
    //Thread.currentThread().setName("休眠一秒线程")
    println(Thread.currentThread().getName)
    Thread.sleep(1000)
  }

}

class DemoThread extends Thread {

  //super.setName("休眠一秒线程")
  //创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
  //Thread.currentThread().setName("休眠一秒线程")
  //val threadName = Thread.currentThread().getName

  override def run(): Unit = {
    //println(s"线程名：$threadName")
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

