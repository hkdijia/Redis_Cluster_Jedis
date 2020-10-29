package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RExpirable;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author HuangKai
 */
public class RedissonString {
    private static final String NODEADDRESS_M1 = "redis://192.168.66.130:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);
        RBucket<Object> bucket = client.getBucket("c2sso:c2smsPhoneCount:188888888888");
//        bucket.set("99999999999");
//        bucket.expire(100000000, TimeUnit.SECONDS);

        long time = bucket.remainTimeToLive();
        System.out.println("时间：" + time);


        //rBucket.set("思密达");
        //System.out.println("是啥呢：" +rBucket.get());
        //rBucket.clearExpire();
        client.shutdown();
    }


}
