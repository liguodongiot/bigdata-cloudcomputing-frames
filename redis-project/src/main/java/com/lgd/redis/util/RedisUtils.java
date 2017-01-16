package com.lgd.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by liguodong on 2016/12/20.
 */
public class RedisUtils {
    private static final String HOST = "192.168.133.252";
    private static final int PORT = 6379;

    private RedisUtils(){}

    public static Jedis getJedis(){
        Jedis jedis = InitPool.getJedisPool().getResource();
        return jedis;
    }

    public static void returnJedis(Jedis jedis){
        //jedisPool.returnResourceObject(jedis);
        jedis.close();
    }

    static class InitPool{
        private static JedisPool jedisPool = null;

        static {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(100);//总连接数
            poolConfig.setMaxIdle(10);//空闲链接数
            poolConfig.setMaxWaitMillis(3000);//创建连接的超时时间
            poolConfig.setTestOnBorrow(true);//在创建连接的时候是否会测试
            jedisPool = new JedisPool(poolConfig,HOST, PORT);
        }

        public static JedisPool getJedisPool(){
            return jedisPool;
        }
    }

}
