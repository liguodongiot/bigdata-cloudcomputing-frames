package com.lgd.scala.thread

import java.util.Random
import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue, CountDownLatch}

/**
  * Created by liguodong on 2017/3/5.
  */
class BlockQueueConsumer(latch: CountDownLatch, blockingQueue: BlockingQueue[Integer])
  extends Runnable{

  override def run(): Unit = {

    while(latch.getCount>0){
      println("队列长度为："+blockingQueue.size())
      val data: Integer = blockingQueue.take()
      println(s"消费数据：$data")
      if(latch.getCount==1 && blockingQueue.isEmpty){
        latch.countDown()
      }
    }
  }

}
