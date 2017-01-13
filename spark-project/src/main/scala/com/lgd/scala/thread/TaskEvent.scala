package com.lgd.scala.thread

/**
  * 任务队列
  *
  * 任务事件
  * Created by liguodong on 2017/1/2.
  */

trait TaskEvent

case class TaskSubmitted(name:String) extends TaskEvent

case class TaskSucceeded(name:String) extends TaskEvent

case class TaskFailed(name:String) extends TaskEvent
