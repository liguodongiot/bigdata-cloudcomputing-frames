package com.lgd.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Describe: ES工具类
 *
 * @author: guodong.li
 * @datetime: 2017/5/22 7:51
 */
public class EsUtils {

    private static TransportClient client;

    private final static String HOSTNAME = "172.22.1.133";
    private final static Integer PORT = 9300;

    private EsUtils() {
    }

//    public TransportClient init() throws UnknownHostException {
//        /**
//         * 1:通过 setting对象来指定集群配置信息
//         */
//        Settings setting = Settings
//                .settingsBuilder()
//                .put("cluster.name", "elasticsearch")//指定集群名称
//                .put("client.transport.sniff", true)//启动嗅探功能
//                .build();
//
//        /**
//         * 2：创建客户端
//         * 通过setting来创建，若不指定则默认链接的集群名为elasticsearch
//         * 链接使用tcp协议即9300
//         */
//        transportClient = TransportClient.builder().settings(setting).build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
//
//        /**
//         * 3：查看集群信息
//         */
//        List<DiscoveryNode> connectedNodes = transportClient.connectedNodes();
//        for(DiscoveryNode node : connectedNodes)
//        {
//            System.out.println("节点主机："+node.getHostAddress());
//        }
//
//        return transportClient;
//    }


    public static TransportClient init() throws UnknownHostException {
        //设置集群名称
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)//启动嗅探功能
                .build();
        //创建client
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOSTNAME), PORT));

        /**
          * 3：查看集群信息
          */
        List<DiscoveryNode> connectedNodes = client.connectedNodes();
        for(DiscoveryNode node : connectedNodes)
        {
            System.out.println("节点主机："+node.getHostAddress());
        }
        return client;
    }


    public static void close(){
        if (client != null) {
            client.close();
        }
    }

}
