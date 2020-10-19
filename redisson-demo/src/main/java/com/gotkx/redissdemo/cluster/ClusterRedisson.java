package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class ClusterRedisson {

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://192.168.50.21:6379");
                //.addNodeAddress("redis://192.168.50.21:6379", "redis://127.0.0.1:7001")
                //.addNodeAddress("redis://127.0.0.1:7002");
        RedissonClient redisson = Redisson.create(config);
        System.out.println(1);
    }
}
