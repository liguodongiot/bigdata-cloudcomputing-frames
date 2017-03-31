package com.lgd.base.activemq;

import javax.jms.JMSException;

/**
 * <p>Title: ConsumerMain</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/3/31 17:27
 */

public class ConsumerMain implements Runnable {

    static Thread t1 = null;

    public static void main(String[] args) throws InterruptedException {

        t1 = new Thread(new ConsumerMain());
        t1.setDaemon(false);
        t1.start();

        /**
         * 如果发生异常，则重启consumer
         */
		/*while (true) {
			System.out.println(t1.isAlive());
			if (!t1.isAlive()) {
				t1 = new Thread(new ConsumerMain());
				t1.start();
				System.out.println("重新启动");
			}
			Thread.sleep(5000);
		}*/
        // 延时500毫秒之后停止接受消息
        // Thread.sleep(500);
        // consumer.close();
    }

    public void run() {
        try {
            ConsumerTool consumer = new ConsumerTool();
            consumer.consumeMessage();
            while (ConsumerTool.isconnection) {
            }
        } catch (Exception e) {
        }

    }
}
