package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author HuangKai
 */
public class RedissonString {
    private static final String NODEADDRESS_M1 = "redis://192.168.50.21:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        RBucket<String> rBucket = client.getBucket("hk");
        rBucket.set("思密达");
        String s = rBucket.get();
        System.out.println("是啥呢：" +s);
        client.shutdown();
    }


}
