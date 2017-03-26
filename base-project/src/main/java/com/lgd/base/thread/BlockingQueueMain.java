package com.lgd.base.thread;

import java.util.concurrent.*;

/**
 * Created by liguodong on 2017/3/26.
 */
public class BlockingQueueMain {

    public static void main(String[] args) {

        //队列超过两个就会阻塞
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);

        // BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        // 不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE

        // BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        BlockingQueueConsumer consumer = new BlockingQueueConsumer(queue,random);
        BlockingQueueProducer producer = new BlockingQueueProducer(queue,random);

        //ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 3; i++) {
            //executorService.submit(producer);
            new Thread(producer, "Producer" + (i + 1)).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(consumer, "Consumer" + (i + 1)).start();
        }

        new Thread(producer, "Producer" + (5)).start();
    }
}
