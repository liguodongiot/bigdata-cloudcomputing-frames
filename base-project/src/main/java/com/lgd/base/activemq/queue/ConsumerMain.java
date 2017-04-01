package com.lgd.base.activemq.queue;

import javax.jms.JMSException;

/**
 * <p>Title: ConsumerMain</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/1 9:22
 */
public class ConsumerMain implements Runnable {

    static ConsumerTool consumer = new ConsumerTool();
    static Thread t1 = null;

    public static void main(String[] args) throws InterruptedException, JMSException {

        t1 = new Thread(new ConsumerMain());
        t1.start();
        while (true) {
//            System.out.println(t1.isAlive());//查看线程是否活着
//            if (!t1.isAlive()) {
//                t1 = new Thread(new ConsumerMain());
//                t1.start();
//                System.out.println("重新启动");
//            }
//            Thread.sleep(5000);
            if(!ConsumerTool.isconnection){
                break;
            }
        }
        // 延时10000毫秒之后停止接受消息
        Thread.sleep(10000);
        consumer.close();
    }

    public void run() {
        try {

            consumer.consumeMessage();

            while (true) {
                if(!ConsumerTool.isconnection){
                    break;
                }
                Thread.sleep(2000);
                System.out.println("等待着。。。");
                //System.out.println(123);
            }
        } catch (Exception e) {
        }
    }
}
