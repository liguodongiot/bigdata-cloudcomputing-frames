
### 运行ActiveMQ
```
解压缩 apache-activemq-5.5.1-bin.zip，
修改配置文件activeMQ.xml，将0.0.0.0修改为localhost

<transportConnectors>
    <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
    <transportConnector name="openwire" uri="tcp://localhost:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="amqp" uri="amqp://localhost:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="stomp" uri="stomp://localhost:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="mqtt" uri="mqtt://localhost:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    <transportConnector name="ws" uri="ws://localhost:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>

然后双击apache-activemq-5.5.1\bin\activemq.bat运行ActiveMQ程序。
启动ActiveMQ以后，登陆：http://localhost:8161/admin/，创建一个Queue，命名为FirstQueue。



1、点对点或队列模型
在点对点或队列模型下，一个生产者向一个特定的队列发布消息，
一个消费者从该队列中读取消息。这里，生产者知道消费者的队列，并直接将消息发送到消费者的队列。
这种模式被概括为：
只有一个消费者将获得消息
生产者不需要在接收者消费该消息期间处于运行状态，
接收者也同样不需要在消息发送时处于运行状态。
每一个成功处理的消息都由接收者签收

2、发布者/订阅者模型
发布者/订阅者模型支持向一个特定的消息主题发布消息。0或多个订阅者可能对接收来自特定消息主题的消息感兴趣。在这种模型下，发布者和订阅者彼此不知道对方。这种模式好比是匿名公告板。
这种模式被概括为：
多个消费者可以获得消息
在发布者和订阅者之间存在时间依赖性。发布者需要建立一个订阅（subscription），以便客户能够订阅。
订阅者必须保持持续的活动状态以接收消息，除非订阅者建立了持久的订阅。
在那种情况下，在订阅者未连接时发布的消息将在订阅者重新连接时重新发布。


```

#### topic
**ProducerMain**
生产者
**ConsumerMain**
消费者

### queue
