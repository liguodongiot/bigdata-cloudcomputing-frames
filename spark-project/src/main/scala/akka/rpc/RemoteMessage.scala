package akka.rpc

/**
  * Created by liguodong on 2017/1/3.
  */

//跨进程通信，要走网络，需要序列化
trait RemoteMessage extends Serializable

case class RegisterWorker(id:String,memory:Int,cores:Int) extends RemoteMessage


