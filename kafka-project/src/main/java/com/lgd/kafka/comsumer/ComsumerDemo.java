package com.lgd.kafka.comsumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liguodong on 2017/2/5.
 */
public class ComsumerDemo {

    private static final String topic = "test";
    private static final Integer threads = 2;

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("zookeeper.connect","192.168.133.252:2181");
        //消费组ID
        props.setProperty("group.id","testgroup");
        //从头开始消费
        props.put("auto.offset.reset","smallest");
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, threads);

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        for(final KafkaStream<byte[], byte[]> kafkaStream : streams){
            new Thread(new Runnable() {
                public void run() {
                    for(MessageAndMetadata<byte[], byte[]> mm : kafkaStream){
                        String msg = new String(mm.message());
                        System.out.println(msg);
                    }
                }
            }).start();
        }


    }
}
