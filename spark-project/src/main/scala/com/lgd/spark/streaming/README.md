
### 流式统计单词
**WordCount**

```
#模拟产生数据
nc -lk 8888
```

### 本地运行spark设置日志级别的2种方式

#### 1、通过配置文件
```
log4j.rootLogger=warn,console
# console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Threshold=info
log4j.appender.console.ImmediateFlush=true
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%-5p] %d %m %x %n
```

#### 2、代码中设置
```
val sc: SparkContext = new SparkContext(sparkConf)
sc.setLogLevel("WARN")
//sc.setLogLevel("DEBUG")
//sc.setLogLevel("ERROR")
//sc.setLogLevel("INFO")
```

### 数据累加

**StatefulWordCount**


### Spark Streaming + Flume 集成

#### Push 方式
**FlumePushWordCount**

```
##配置Flume:

agent.sinks = avroSink
agent.sinks.avroSink.type = avro
agent.sinks.avroSink.channel = memoryChannel
agent.sinks.avroSink.hostname = <chosen machine's hostname>
agent.sinks.avroSink.port = <chosen port on the machine>

##eg:

# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# source
a1.sources.r1.type = spooldir
a1.sources.r1.spoolDir = /home/liguodong/data/
a1.sources.r1.fileHeader = true

# Describe the sink
a1.sinks.k1.type = avro
#这是接收方 Spark的Executor端的ip和端口
a1.sinks.k1.hostname = 192.168.133.252
a1.sinks.k1.port = 8888

# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1


##启动flume:
bin/flume-ng  agent -n a1 -c conf -f conf-dev/flume-push.conf

bin/spark-submit --master spark://192.168.133.252:7077 \
--class com.lgd.spark.streaming.FlumePushWordCount \
/home/liguodong/code/original-spark-1.0.jar 192.168.133.252 8888 

```


#### Pull 方式
**FlumePollWordCount**
```
##配置Flume:

agent.sinks = spark
agent.sinks.spark.type = org.apache.spark.streaming.flume.sink.SparkSink
agent.sinks.spark.hostname = <hostname of the local machine>
agent.sinks.spark.port = <port to listen on for connection from Spark>
agent.sinks.spark.channel = memoryChannel

##eg:

# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# source
a1.sources.r1.type = spooldir
a1.sources.r1.spoolDir = /home/liguodong/data/
a1.sources.r1.fileHeader = true

# Describe the sink
a1.sinks.k1.type = org.apache.spark.streaming.flume.sink.SparkSink
#Flume的ip和端口
a1.sinks.k1.hostname = 192.168.133.252
a1.sinks.k1.port = 8888

# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1


将commons-lang3-3.5.jar、scala-library-2.11.7.jar、spark-streaming-flume-sink_2.11-2.1.0.jar
拷贝到${FLUME_HOME}/lib/目录下

##启动flume:
bin/flume-ng  agent -n a1 -c conf -f conf-dev/flume-push.conf
```

### Spark Streaming + Kafka 集成

```
启动zookeeper服务
bin/zookeeper-server-start.sh config/zookeeper.properties
启动Kafka
bin/kafka-server-start.sh config/server.properties >/dev/null 2>&1 &


创建一个"test"的topic，一个副本,一个分区
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

查看主题
bin/kafka-topics.sh --list --zookeeper localhost:2181

查看主题详情
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
```

####基于Receivers的方法
**KafkaWordCount**

#### 直连方式
**KafkaDirectWordCount**

### 窗口函数
**WindowOpts**
```
#模拟产生数据
nc -lk 9999
```