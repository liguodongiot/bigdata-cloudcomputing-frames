package com.lgd.base.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liguodong on 2017/3/26.
 */
public class BlockingQueueProducer implements Runnable {

    private BlockingQueue<String> queue;
    private ThreadLocalRandom random;

    public BlockingQueueProducer(BlockingQueue<String> queue,
                                 ThreadLocalRandom random) {
        this.queue = queue;
        this.random = random;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(random.nextInt(10));
                String task = Thread.currentThread().getName() + " made a product " + i;

                System.out.println(task);
                queue.put(task);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

}
