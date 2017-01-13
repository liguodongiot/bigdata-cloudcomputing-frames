package com.lgd.akka.communication

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory


/**
  * Created by liguodong on 2017/1/2.
  */
class Worker(host:String,port:Int) extends Actor{

  var master: ActorSelection = _

  //建立连接 找到Master
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@$host:$port/user/Master")
    master ! "connect"
  }

  override def receive: Receive = {
    case "reply" => {
      println("a reply from master")
    }
  }
}

object Worker{
  def main(args: Array[String]): Unit = {

//    val host = args(0)
//    val port = args(1).toInt
//    val hostMaster = args(2)
//    val portMaster = args(3).toInt

    val host = "127.0.0.1"
    val port = "8899".toInt
    val hostMaster = "127.0.0.1"
    val portMaster = "8888".toInt

    //准备配置
    val configStr =
    s"""
       |akka.com.lgd.scala.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = "$host"
       |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor,单例对象
    val actorSystem = ActorSystem("WorkerSystem",config)

    //创建Actor
    actorSystem.actorOf(Props(new Worker(hostMaster,portMaster)),"Worker")

    actorSystem.awaitTermination()

  }
}
