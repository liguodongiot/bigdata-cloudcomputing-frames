package com.lgd.scala.netty.rpc.message

/**
  * Created by liguodong on 2017/3/21.
  */
trait Message extends Serializable

case class RegiesterMsg(content: String) extends Message

case class ReplyMsg(content: String) extends Message