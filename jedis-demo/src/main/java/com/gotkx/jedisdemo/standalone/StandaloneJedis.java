package com.gotkx.jedisdemo.standalone;

import redis.clients.jedis.Jedis;

/**
 * @author HuangKai
 * jedis 连接redis（单机）
 */
public class StandaloneJedis {

    public static String ADDRESS = "127.0.0.1";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Jedis jedis = new Jedis(ADDRESS);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }

}
