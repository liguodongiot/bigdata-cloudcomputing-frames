package com.lgd.base.activemq;

import javax.jms.JMSException;
import java.util.Random;

/**
 * <p>Title: ProducerMain</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/3/31 17:26
 */
public class ProducerMain {

    public static void main(String[] args) throws JMSException, Exception {
        //ProducerTool producer = new ProducerTool();
        ProducerTool producer = ProducerTool.getInstance();

        Random random = new Random();
        for(int i=0;i<20;i++){
            Thread.sleep(random.nextInt(10)*1000);
            producer.produceMessage("Hello, world!--"+i);
        }
        producer.close();
    }

}
