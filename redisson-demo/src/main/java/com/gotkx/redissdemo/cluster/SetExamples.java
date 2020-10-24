package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 使用 RList 操作 Redis 列表
 * @author HuangKai
 */
public class SetExamples {

    private static final String  NODEADDRESS_M1 = "redis://192.168.50.21:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        RSet<String> rSet = client.getSet("rr:0:admin");
        for (String s : rSet) {
            System.out.println(s);
        }

        client.shutdown();
    }
}
