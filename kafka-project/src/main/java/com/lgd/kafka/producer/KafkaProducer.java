package com.lgd.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;

/**
 * Created by liguodong on 2017/1/1.
 */
public class KafkaProducer extends Thread{

    private String topic;

    public KafkaProducer(String topic){
        super();
        this.topic = topic;
    }

    @Override
    public void run(){

        for (int i = 0; i < 100; i++) {
            String message = "message"+i;
            Producer<Integer,String> producer = createProducer();
            producer.send(new KeyedMessage<Integer, String>(topic,message));
            System.out.println("发送："+message);
        }

    }

    public Producer<Integer,String> createProducer(){

        Properties props = new Properties();
        props.setProperty("zookeeper.connect","192.168.133.252:2181");
        //序列化
        props.setProperty("serializer.class", StringEncoder.class.getName());
        //broker
        props.setProperty("metadata.broker.list","192.168.133.252:9092");

        return new Producer<Integer,String>(new ProducerConfig(props));
    }

    public static void main(String[] args) {
        new KafkaProducer("test").start();
    }

}
