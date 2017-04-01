package com.lgd.base.activemq.queue;

/**
 * <p>Title: ProducerMain</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/1 9:23
 */
public class ProducerMain {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ProducerTool producer = new ProducerTool();
        producer.produceMessage("Hello, world!");
        producer.close();
    }
}
