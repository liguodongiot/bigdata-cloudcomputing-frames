package com.lgd.es;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.List;

/**
 * Describe: es获取客户端-使用反射方式
 * 这种方式效率明显高于new客户端，并可避免线上环境内存溢出和超时等问题
 *
 * @author: guodong.li
 * @datetime: 2017/5/24 17:50
 */
public class EsReflexUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(EsReflexUtils.class);

    // 创建私有对象
    private static TransportClient client;

    public static void init() {
        // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，
        // 把集群中其它机器的ip地址加到客户端中，
        Settings settings = Settings.builder()
                .put("cluster.name", "es-clustername")
                .put("client.transport.sniff", true)
                .build();
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        try {
            clazz = Class.forName(PreBuiltTransportClient.class.getName());
            constructor = clazz.getDeclaredConstructor(Settings.class,Class[].class);
            constructor.setAccessible(true);
            //EmptyPlugin 空插件 形式而已
            client = (TransportClient) constructor.newInstance(settings,new Class[]{EmptyPlugin.class});
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.250.140.215"), 9300));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("初始化异常。。。"+e);
        }

        /**
         * 查看集群信息
         */
        List<DiscoveryNode> connectedNodes = client.connectedNodes();
        StringBuilder hosts = new StringBuilder("");
        for(DiscoveryNode node : connectedNodes) {
            hosts.append("[").
                    append(node.getHostAddress())
                    .append("]");
        }
        LOGGER.info("ES connect hosts:"+hosts);
    }

    public static TransportClient getTransportClient() {
        return client;
    }

    public static void close(){
        if (client != null) {
            client.close();
        }

    }

}
