package com.lgd.redis.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * redis集群操作
 * Created by liguodong on 2016/12/23.
 */

public class Demo {

    public static void main(String[] args) {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();

        //实际上可以只写一个节点，添加这几个节点，只需保证有一个存在，就能访问。
        HostAndPort hostAndPort0 = new HostAndPort("192.168.1.160", 7000);
        HostAndPort hostAndPort1 = new HostAndPort("192.168.1.160", 7001);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.1.160", 7002);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.1.160", 7003);
        HostAndPort hostAndPort4 = new HostAndPort("192.168.1.160", 7004);
        HostAndPort hostAndPort5 = new HostAndPort("192.168.1.160", 7005);

        nodes.add(hostAndPort0);
        nodes.add(hostAndPort1);
        nodes.add(hostAndPort2);
        nodes.add(hostAndPort3);
        nodes.add(hostAndPort4);
        nodes.add(hostAndPort5);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setTestOnBorrow(true);

        JedisCluster jedisCluster = new JedisCluster(nodes, jedisPoolConfig );

        String string = jedisCluster.get("aa");
        System.out.println(string);
    }
}
