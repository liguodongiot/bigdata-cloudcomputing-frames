package com.lgd.scala.netty.rpc.handler

import com.lgd.scala.netty.rpc.message.{RegiesterMsg, ReplyMsg}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * Created by liguodong on 2017/3/21.
  */
class ClientHandler extends ChannelInboundHandlerAdapter {

  override def channelActive(ctx: ChannelHandlerContext): Unit = {
        println("1.channelActive")
    //    val content = "hello server"
    //    ctx.writeAndFlush(Unpooled.copiedBuffer(content.getBytes("UTF-8")))
    ctx.writeAndFlush(RegiesterMsg("hello server"))
  }


  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {

    println("2.channelRead")
    //    val byteBuf = msg.asInstanceOf[ByteBuf]
    //    val bytes = new Array[Byte](byteBuf.readableBytes())
    //    byteBuf.readBytes(bytes)
    //    val message = new String(bytes, "UTF-8")
    //    println(message)
    msg match {
      case ReplyMsg(content) => {
        println(content)
      }
    }
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    println("3.channelReadComplete")
    ctx.flush()
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    println("exceptionCaught")
    ctx.close()
  }
}
