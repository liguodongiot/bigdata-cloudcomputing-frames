package akka.rpc

/**
  * Worker的信息
  * Created by liguodong on 2017/1/3.
  */

class WorkerInfo(val id:String,val memory:Int,val cores:Int) {

  //TODO 上一次心跳
  var lastHeartBeatTime:Long = _

}
