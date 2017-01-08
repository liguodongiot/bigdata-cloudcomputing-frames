package akka.rpc

/**
  * Created by liguodong on 2017/1/3.
  */

//跨进程通信，要走网络，需要序列化
trait RemoteMessage extends Serializable

//Worker --> Master     Worker的ID 内存 CPU
case class RegisterWorker(id:String,memory:Int,cores:Int) extends RemoteMessage

//Master --> Worker
case class RegisteredWorker(val masterUrl:String) extends RemoteMessage

//进程内部发送消息不需要序列化
//Worker --> self
case object SendHeartBeat

//Worker --> Master
case class HeartBeat(val workerId:String) extends RemoteMessage

//Master --> self
case object CheckTimeoutWorker

//Master --> Worker
//case class