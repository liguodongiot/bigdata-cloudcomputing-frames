package com.lgd.redis.test;

import com.lgd.redis.util.RedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Created by liguodong on 2016/12/20.
 */
public class RedisCommonTest {

    @Test
    public void test(){
        Jedis jedis = RedisUtils.getJedis();
        String age = jedis.get("age");
        System.out.println(age);
        RedisUtils.returnJedis(jedis);
    }


    @Test
    public void testNoPipeline(){
        Jedis jedis = RedisUtils.getJedis();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            jedis.set("test"+i, "test"+i);
        }

        System.out.println(System.currentTimeMillis()-startTime);

        RedisUtils.returnJedis(jedis);
    }

    /**
     * 使用管道
     */
    @Test
    public void testPipeline(){
        Jedis jedis = RedisUtils.getJedis();

        long startTime = System.currentTimeMillis();
        Pipeline pipelined = jedis.pipelined();
        for (int i = 0; i < 1000; i++) {
            pipelined.set("test"+i, "test"+i);
        }
        //pipelined.sync();
        List<Object> returnList = pipelined.syncAndReturnAll();
        System.out.println(returnList.size()==1000);

        System.out.println(System.currentTimeMillis()-startTime);

        RedisUtils.returnJedis(jedis);
    }

}
