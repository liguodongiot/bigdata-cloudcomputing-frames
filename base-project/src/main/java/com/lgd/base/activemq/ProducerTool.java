package com.lgd.base.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <p>Title: ProducerTool</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/3/31 15:50
 */
public class ProducerTool {

    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private String subject = "mytopic";
    private Destination destination = null;
    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;

    private static ProducerTool instance = new ProducerTool();
    private ProducerTool() {
        try {
            initialize();
            //打开连接
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static ProducerTool getInstance(){
        return instance;
    }

    // 初始化
    private void initialize() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                user, password, url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(subject);
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    // 发送消息
    public void produceMessage(String message) throws JMSException {
        //initialize();

        TextMessage msg = session.createTextMessage(message);
        System.out.println("Producer:->Sending message: " + message);
        producer.send(msg);
        System.out.println("Producer:->Message sent complete!");
    }

    // 关闭连接
    public void close() throws JMSException {
        System.out.println("Producer:->Closing connection");
        if (producer != null)
            producer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

}
