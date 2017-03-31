
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
```

**ProducerMain**
生产者

**ConsumerMain**
消费者


