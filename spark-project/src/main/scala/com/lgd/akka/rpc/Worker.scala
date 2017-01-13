package com.lgd.akka.rpc

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created by liguodong on 2017/1/2.
  */

class Worker(host:String,port:Int,val memory:Int,val cores:Int) extends Actor{

  var master: ActorSelection = _

  val workerId  = UUID.randomUUID().toString

  //发送心跳时间间隔
  final val HEART_INTERVAL = 10000


  //建立连接 找到Master
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@$host:$port/user/Master")
    master ! RegisterWorker(workerId,memory,cores)
  }

  override def receive: Receive = {
    case RegisteredWorker(masterUrl) => {
      println(masterUrl)

      import context.dispatcher
      //启动定时器，发送心跳
      context.system.scheduler.schedule(0 millis,HEART_INTERVAL millis,self,SendHeartBeat)
    }
    case SendHeartBeat =>{
      println("send heartbeat to master")
      master ! HeartBeat(workerId)
    }

  }
}

object Worker{
  def main(args: Array[String]): Unit = {

//    val host = args(0)
//    val port = args(1).toInt
//    val hostMaster = args(2)
//    val portMaster = args(3).toInt
//
//    val memory = args(4).toInt
//    val cores = args(5).toInt


    val host = "127.0.0.1"
    val port = "9999".toInt
    val hostMaster = "127.0.0.1"
    val portMaster = "8888".toInt
    val memory = 20480
    val cores = 24


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
    actorSystem.actorOf(Props(new Worker(hostMaster,portMaster,memory,cores)),"Worker")

    actorSystem.awaitTermination()

  }
}
