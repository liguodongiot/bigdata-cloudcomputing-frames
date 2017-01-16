package com.lgd.redis.sentinel;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * java操作sentinel
 *
 * Created by liguodong on 2016/12/23.
 */
public class Demo {
    private static final String HOST = "192.168.133.252";
    private static final int PORT = 6379;

    public static void main(String[] args) {
        //sentinel的ip地址和端口
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.133.252:26379");
        //sentinels.add("192.168.1.170:26379");

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setTestOnBorrow(true);

        //masterName参数是redis中sentinel.conf 所设置的
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",sentinels,
                jedisPoolConfig);

        HostAndPort hostAndPort = jedisSentinelPool.getCurrentHostMaster();
        System.out.println(hostAndPort.getHost()+":"+hostAndPort.getPort());

        Jedis jedis = jedisSentinelPool.getResource();
        String value = jedis.get("age");
        System.out.println(value);
    }
}
