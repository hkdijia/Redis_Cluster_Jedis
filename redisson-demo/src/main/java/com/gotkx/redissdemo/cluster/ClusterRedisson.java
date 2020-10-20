package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;

public class ClusterRedisson {

    //private static  String  NODEADDRESS = "redis://192.168.50.21:6379";
    private static final String  NODEADDRESS_M1 = "redis://10.254.13.20:6379";
    private static final String  NODEADDRESS_S1 = "redis://10.254.12.239:6380";
    private static final String  NODEADDRESS_M2 = "redis://10.254.13.30:6379";
    private static final String  NODEADDRESS_S2 = "redis://10.254.13.30:6380";
    private static final String  NODEADDRESS_M3 = "redis://10.254.13.35:6379";
    private static final String  NODEADDRESS_S3 = "redis://10.254.13.35:6380";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
//                .addNodeAddress(NODEADDRESS_S1)
//                .addNodeAddress(NODEADDRESS_M2)
//                .addNodeAddress(NODEADDRESS_S2)
//                .addNodeAddress(NODEADDRESS_M3)
//                .addNodeAddress(NODEADDRESS_S3);
                //.addNodeAddress("redis://192.168.50.21:6379", "redis://127.0.0.1:7001")
                //.addNodeAddress("redis://127.0.0.1:7002");
        RedissonClient redisson = Redisson.create(config);

        //首先获取redis中的key-value对象，key不存在没关系
        RBucket<String> keyObject = redisson.getBucket("key");
        //如果key存在，就设置key的值为新值value
        //如果key不存在，就设置key的值为value
        keyObject.set("123");
        //String string = redisson.getBucket("key")
        //System.out.println(string);
        //最后关闭RedissonClient
        redisson.shutdown();
    }
}
