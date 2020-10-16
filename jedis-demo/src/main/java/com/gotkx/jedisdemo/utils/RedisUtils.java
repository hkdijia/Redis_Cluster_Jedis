package com.gotkx.jedisdemo.utils;

import org.omg.CORBA.TIMEOUT;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtils {

    private static final String HOST = ;
    private static final int PORT = ;
    private static final boolean PASSWORD = ;

    public static void main(String[] args) {
        // 创建JedisPool所需的连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        // 最大连接数，默认8
        poolConfig.setMaxTotal(1024);

        // 最大空闲数,默认8
        poolConfig.setMaxIdle(100);

        // poolConfig 各种配置

        /// 是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(true);

        // 创建JedisPool连接池
        jedisPool = new JedisPool(poolConfig, HOST, PORT, TIMEOUT, PASSWORD);
    }

}
