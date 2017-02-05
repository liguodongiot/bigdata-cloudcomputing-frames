package com.lgd.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;

/**
 * Created by liguodong on 2017/2/5.
 */
public class ProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        //props.setProperty("zookeeper.connect","192.168.133.252:2181");
        //序列化
        props.setProperty("serializer.class", StringEncoder.class.getName());
        //broker
        props.setProperty("metadata.broker.list","192.168.133.252:9092");

        Producer<Integer,String> producer = new Producer<Integer,String>(
                new ProducerConfig(props));

        String topic = "test";
        for (int i = 0; i < 100; i++) {
            producer.send(new KeyedMessage<Integer, String>(topic,"msg"+i));
        }
    }

}
