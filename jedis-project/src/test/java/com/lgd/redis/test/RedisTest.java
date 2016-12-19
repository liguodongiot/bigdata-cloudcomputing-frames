package com.lgd.redis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by liguodong on 2016/12/19.
 */
public class RedisTest {

    /**
     * 使用java代码操作redis
     * 一般只用于测试代码
     */
    @Test
    public void test(){
        Jedis jedis = new Jedis("192.168.133.252",6379);
        //权限认证
        jedis.auth("admin");
        System.out.println(jedis.get("name"));

        jedis.set("sex","male");
        System.out.println(jedis.get("sex"));

    }

}
