package com.lgd.kafka.comsumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liguodong on 2017/1/1.
 */

public class KafkaComsumer extends Thread{

    private String topic;

    public KafkaComsumer(String topic){
        super();
        this.topic = topic;
    }

    @Override
    public void run(){
        //创建Consumer
        ConsumerConnector consumer = CreateComsumer();

        Map<String,Integer> topicCountMap = new HashMap<String,Integer>();
        topicCountMap.put(topic,1);

        Map<String,List<KafkaStream<byte[],byte[]>>> messageStream = consumer.createMessageStreams(topicCountMap);

        KafkaStream<byte[],byte[]> kafkaStream;
        kafkaStream = messageStream.get(topic).get(0);
        ConsumerIterator<byte[],byte[]> iterator = kafkaStream.iterator();

        while(iterator.hasNext()){
            byte[] message = iterator.next().message();
            System.out.println("接收到："+new String(message));
        }
    }

    private ConsumerConnector CreateComsumer(){
        Properties props = new Properties();
        props.setProperty("zookeeper.connect","192.168.133.252:2181");
        //消费组ID
        props.setProperty("group.id","testgroup");

        return Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }


    public static void main(String[] args){
        new KafkaComsumer("test").start();
    }


}

