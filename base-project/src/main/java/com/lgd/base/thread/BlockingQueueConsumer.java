package com.lgd.base.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liguodong on 2017/3/26.
 */
public class BlockingQueueConsumer implements Runnable {

    private BlockingQueue<String> queue;
    private ThreadLocalRandom random;

    public BlockingQueueConsumer(BlockingQueue<String> queue,
                                 ThreadLocalRandom random){
        this.queue = queue;
        this.random = random;
    }

    public void run() {
        try {
            Thread.sleep(random.nextInt(10));

            System.out.println(Thread.currentThread().getName()+ " trying...");
            String temp = queue.take();//如果队列为空，会阻塞当前线程
            System.out.println(Thread.currentThread().getName() + " get a job " +temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
