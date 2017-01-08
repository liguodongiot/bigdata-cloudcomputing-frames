package akka.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * Created by liguodong on 2017/1/2.
  */

class Master(val host:String,val port:Int) extends Actor{

  println("调用主构造器")
  //保存注册的worker
  // WorkerId -> WorkerInfo
  val idToWorker = new mutable.HashMap[String,WorkerInfo]()

  // WorkerInfo
  val workers = new mutable.HashSet[WorkerInfo]()

  //超时检查的间隔
  final val CHECK_INTERVAL = 15000

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    println("调用preStart方法")

    import context.dispatcher
    //定时检查Worker
    context.system.scheduler.schedule(0 millis,CHECK_INTERVAL millis,self,CheckTimeoutWorker)

  }

  override def receive: Receive = {
    case RegisterWorker(id,memory,cores) => {

      //判断是否已经注册过，如果没有注册过，把Worker的信息封装起来保存在内存当中
      if(!idToWorker.contains(id)){
        val workerInfo = new WorkerInfo(id,memory,cores)
        idToWorker(id) = workerInfo
        workers += workerInfo
        //Master 反馈给 Worker 回复
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@$host:$port/user/Master")
      }

    }
    case HeartBeat(workerId) => {

      if(idToWorker.contains(workerId)){
        val workerInfo = idToWorker(workerId)
        //报活
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartBeatTime = currentTime
      }
    }
    case CheckTimeoutWorker =>{

      val currentTime = System.currentTimeMillis()

      //需要移除的Worker
      val toRemove = workers.filter(currentTime - _.lastHeartBeatTime > CHECK_INTERVAL)
      for (w<- toRemove){
        workers -= w
        idToWorker -= w.id
      }
      println("active worker num:"+workers.size)
    }

  }
}


object Master {

  def main(args: Array[String]): Unit = {

//    val host = args(0)
//    val port = args(1).toInt
    val host = "127.0.0.1"
    val port = "8888".toInt

    //准备配置
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor,单例对象
    val actorSystem = ActorSystem("MasterSystem",config)

    //创建Actor
    val master = actorSystem.actorOf(Props(new Master(host,port)),"Master")

    actorSystem.awaitTermination()
    //Await.result()
  }

}
