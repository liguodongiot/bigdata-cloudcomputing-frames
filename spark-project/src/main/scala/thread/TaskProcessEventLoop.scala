package thread

/**
  * Created by liguodong on 2017/1/2.
  */
class TaskProcessEventLoop(name:String) extends EventLoop[TaskEvent](name){
  /**
    * Invoked in the event thread when polling events from the event queue.
    *
    * Note: Should avoid calling blocking actions in `onReceive`, or the event thread will be blocked
    * and cannot process events in time. If you want to call some blocking actions, run them in
    * another thread.
    */
  override protected def onReceive: PartialFunction[TaskEvent, Unit] = {  //偏函数, 不同的任务作出不同的处理
    case TaskSubmitted(name) => {
      println(name + " submitted")
    }

    case TaskSucceeded(name) => {
      println(name + " succeeded")
    }

    case TaskFailed(name) => {
      println(name + " filled")
    }
  }


  /**
    * Invoked if `onReceive` throws any non fatal error. Any non fatal error thrown from `onError`
    * will be ignored.
    */
  override protected def onError(e: Throwable): Unit = {

  }


  //父类有实现必须写override
  /**
    * Invoked when `start()` is called but before the event thread starts.
    */
  override protected def onStart(): Unit = {
    println("on start invoke")
  }

  /**
    * Invoked when `stop()` is called and the event thread exits.
    */
  override protected def onStop(): Unit = {
    println("on stop invoke")
  }

}
