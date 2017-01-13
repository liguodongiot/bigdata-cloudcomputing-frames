package com.lgd.scala.actor

import scala.actors.Actor

/**
  * Created by liguodong on 2016/12/30.
  */
class MyActor extends Actor {

  override def act(): Unit = {
    while (true) {
      //receive 是一个偏函数
      receive {
        case "start" => {
          println("starting ...")
          Thread.sleep(5000)
          println("started")
        }
        case "stop" => {
          println("stopping ...")
          Thread.sleep(5000)
          println("stopped ...")
        }
        case "exit" =>{
          exit()
        }
      }
    }
  }
}


object MyActor {
  def main(args: Array[String]) {
    val actor = new MyActor
    actor.start()
    actor ! "start"
    actor ! "stop"
    println("消息发送完成！")

    Thread.sleep(10000)
    actor ! "exit"

  }
}

