package com.lgd.scala.thread

import java.util.Random
import java.util.concurrent.{ArrayBlockingQueue, CountDownLatch}

/**
  * Created by liguodong on 2017/3/5.
  */
class BlockQueueProducer(latch: CountDownLatch, blockingQueue: ArrayBlockingQueue[Integer])
  extends Runnable{

  override def run(): Unit = {
    try {
      val random: Random = new Random()
      val data = random.nextInt()
      println(s"生产数据$data")
      blockingQueue.put(data)
    } finally {
      latch.countDown()
    }
  }

}
