package com.lgd.redis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by liguodong on 2016/12/19.
 */
public class RedisTest {

    String host = "192.168.133.252";
    int port = 6379;
    Jedis jedis = new Jedis(host, port);


    /**
     * 单机单链接方式
     *
     * 使用java代码操作redis
     * 一般只用于测试代码
     */
    @Test
    public void test(){
        //权限认证
        jedis.auth("admin");
        System.out.println(jedis.get("name"));

        jedis.set("sex","male");
        System.out.println(jedis.get("sex"));
    }


    /**
     * 单机连接池方式
     * @throws Exception
     */
    @Test
    public void testPool() throws Exception {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);//总连接数
        poolConfig.setMaxIdle(10);//空闲链接数
        poolConfig.setMaxWaitMillis(3000);//创建连接的超时时间
        poolConfig.setTestOnBorrow(true);//在创建连接的时候是否会测试


        JedisPool jedisPool = new JedisPool(poolConfig, host, port,0,"admin");
        Jedis jedis = jedisPool.getResource();

        String string = jedis.get("name");
        System.out.println(string);
        jedisPool.returnResourceObject(jedis);


    }

    /**
     * 限制用户访问频率
     * @throws Exception
     */
    @Test
    public void testKeepUserVisit() throws Exception {
        jedis.auth("admin");
        for(int i=0;i<20;i++){
            boolean checkLogin = checkLogin("192.168.1.1");
            System.out.println(checkLogin);
        }
    }


    public boolean checkLogin(String ip){
        String value = jedis.get(ip);
        if(value==null){
            jedis.set(ip, 0+"");
            jedis.expire(ip, 60);
        }else{
            int parseInt = Integer.parseInt(value);
            if(parseInt>=10){
                System.out.println("访问受限。。。。");
                return false;
            }
        }
        jedis.incr(ip);
        return true;
    }

    @Test
    public void testTransaction() throws Exception {
        jedis.auth("admin");
        String value = jedis.get("age");
        System.out.println("休息一会。。。");
        Thread.sleep(5000);
        Transaction multi = jedis.multi();
        int parseInt = Integer.parseInt(value);
        parseInt++;
        multi.set("age", parseInt+"");

        List<Object> exec = multi.exec();
        System.err.println(exec);

    }

    @Test
    public void testWatch() throws Exception {
        jedis.auth("admin");
        jedis.watch("age");
        String value = jedis.get("age");
        System.out.println("休息一会。。。");
        Thread.sleep(5000);
        Transaction multi = jedis.multi();
        int parseInt = Integer.parseInt(value);
        parseInt++;
        multi.set("age", parseInt+"");
        List<Object> exec = multi.exec();

        if(exec==null||exec.size()==0){
            System.err.println("值被修改，执行失败");
            testWatch();
        }

    }



}
