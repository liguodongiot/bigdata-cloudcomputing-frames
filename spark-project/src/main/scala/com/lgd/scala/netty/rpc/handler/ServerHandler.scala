package com.lgd.scala.netty.rpc.handler


import com.lgd.scala.netty.rpc.message.{RegiesterMsg, ReplyMsg}
import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}

/**
  * Created by liguodong on 2017/3/21.
  */
class ServerHandler extends ChannelInboundHandlerAdapter {

  /**
    * 有客户端建立连接后调用
    * @param ctx
    */
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    println("1.channelActive invoked")
  }

  /**
    * 接受客户端发送来的消息
    * @param ctx
    * @param msg
    */
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
        println("2.channelRead invoked")
    //    val byteBuf = msg.asInstanceOf[ByteBuf]
    //    val bytes = new Array[Byte](byteBuf.readableBytes())
    //    byteBuf.readBytes(bytes)
    //    val message = new String(bytes, "UTF-8")
    //    println(message)

    //    val back = "Good Boy!"
    //    val resp = Unpooled.copiedBuffer(back.getBytes("UTF-8"))
    //    ctx.write(resp)
    msg match {
      case RegiesterMsg(content) => {
        println(content)
      }
    }
    ctx.write(ReplyMsg("Hello client..."))

  }

  /**
    * //将消息队列中的数据写入到SocketChannel并发送给对方
    * @param ctx
    */
  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    println("3.channelReadComplete invoked")
    ctx.flush()
  }

  /**
    * 发生异常是关闭ChannelHandlerContext
    * @param ctx
    * @param cause
    */
  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    println("exceptionCaught invoked")
    ctx.close()
  }

}

