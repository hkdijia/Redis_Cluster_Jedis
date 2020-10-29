package com.gotkx.redissdemo.standalone;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class StandaloneRedisson {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 默认连接地址 127.0.0.1:6379
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.66.133:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RBucket<Object> bucket = redissonClient.getBucket("c2sso:c2smsPhoneCount:188888888888");
        //bucket.set("hhhhhhhhhhhhhhhhhhh");
        //bucket.expire(100000000,TimeUnit.SECONDS);
        Object o = bucket.get();
        long time = bucket.remainTimeToLive();

        System.out.println("时间：" + time);
    }

}
