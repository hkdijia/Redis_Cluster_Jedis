package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;

public class SetExample {

    private static final String  NODEADDRESS_M1 = "redis://10.254.8.103:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);
        RSet<String> set = client.getSet("rr:6JBMiO12RgaXo-uO2xGWKw:default");
        for (String s : set) {
            System.out.println(s);
        }
        //set.add(new String("www.baidu.com"));
        //set.add(new String("momomomo"));
        //set.clear();
        client.shutdown();
    }
}
