package com.lgd.scala.netty.rpc.server

import com.lgd.scala.netty.rpc.handler.ServerHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}

/**
  * Created by liguodong on 2017/3/21.
  */
class NettyServer {

  def bind(host: String, port: Int): Unit = {
    //配置服务端线程池组
    //用于服务器接收客户端的连接
    val bossGroup = new NioEventLoopGroup()

    //用户进行SocketChannel的网络读写
    val workerGroup = new NioEventLoopGroup()

    try {
      //是Netty用户启动NIO服务端的辅助启动类，降低服务端的开发复杂度
      val bootstrap = new ServerBootstrap()

      //将两个NIO线程组作为参数传入到ServerBootstrap
      bootstrap.group(bossGroup, workerGroup)
        //创建NioServerSocketChannel
        .channel(classOf[NioServerSocketChannel])
        //绑定I/O事件处理类
        .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline().addLast(
            new ObjectEncoder,
            new ObjectDecoder(ClassResolvers.cacheDisabled(getClass.getClassLoader)),
            new ServerHandler
          )
        }
      })
      //绑定端口，调用sync方法等待绑定操作完成
      val channelFuture = bootstrap.bind(host, port).sync()
      //等待服务关闭
      channelFuture.channel().closeFuture().sync()
    } finally {
      //优雅的退出，释放线程池资源
      bossGroup.shutdownGracefully()
      workerGroup.shutdownGracefully()
    }
  }
}

object NettyServer {
  def main(args: Array[String]) {
    val host = "127.0.0.1"//args(0)
    val port = 8889 //args(1).toInt
    val server = new NettyServer
    server.bind(host, port)
  }
}
