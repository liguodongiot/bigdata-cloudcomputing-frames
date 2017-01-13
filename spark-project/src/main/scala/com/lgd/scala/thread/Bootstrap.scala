package com.lgd.scala.thread

/**
  * Created by liguodong on 2017/1/2.
  */
object Bootstrap {

  def main(args: Array[String]): Unit = {

    val eventLoop = new TaskProcessEventLoop("task-event-loop")
    eventLoop.start()

    for(i <- 1 to 10){
      eventLoop.post(TaskSubmitted(s"task-$i"))
    }

    Thread.sleep(10000)
    eventLoop.stop()

  }

}
