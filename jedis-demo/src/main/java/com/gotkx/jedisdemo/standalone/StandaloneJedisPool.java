package com.gotkx.jedisdemo.standalone;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * @author  HuangKai
 * jedisPool连接redis （单机）
 */
public class StandaloneJedisPool {

    public static String ADDRESS = "127.0.0.1";
    public static int PORT = 6379;

    public static void main(String[] args) {
        //创建连接池对象
        JedisPool jedispool = new JedisPool(ADDRESS,PORT);
        //从连接池中获取一个连接
        Jedis  jedis = jedispool.getResource();
        //使用jedis操作redis
        jedis.set("test", "my forst jedis");
        String str = jedis.get("test");
        System.out.println(str);
        //使用完毕 ，关闭连接，连接池回收资源
        jedis.close();
        //关闭连接池
        jedispool.close();
    }

}
