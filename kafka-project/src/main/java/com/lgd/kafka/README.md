

```
启动zookeeper服务
bin/zookeeper-server-start.sh config/zookeeper.properties
启动Kafka
bin/kafka-server-start.sh config/server.properties >/dev/null 2>&1 &


创建一个"test"的topic，一个副本,一个分区
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic jsontest



删除topic
bin/kafka-topics.sh  --delete --zookeeper localhost:2181  --topic test


查看主题
bin/kafka-topics.sh --list --zookeeper localhost:2181

查看主题详情
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test

创建生产者 producer
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic access_log

创建消费者 consumer
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic access_log --from-beginning

bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic jsontest --from-beginning


```

