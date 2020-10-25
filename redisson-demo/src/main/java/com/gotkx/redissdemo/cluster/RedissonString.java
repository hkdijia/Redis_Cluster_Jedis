package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
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
        client.getBucket("hk").set("ggg");
        //RBucket<Object> rBucket = client.getBucket("hk");
        //rBucket.delete();



        //rBucket.set("思密达");
        //System.out.println("是啥呢：" +rBucket.get());

        //rBucket.clearExpire();

        client.shutdown();
    }


}
