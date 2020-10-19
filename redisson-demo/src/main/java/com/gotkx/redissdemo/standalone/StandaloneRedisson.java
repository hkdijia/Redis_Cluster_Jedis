package com.gotkx.redissdemo.standalone;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class StandaloneRedisson {


    public static void main(String[] args) {
        // 默认连接地址 127.0.0.1:6379
        RedissonClient redisson = Redisson.create();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println(1);
    }

}
