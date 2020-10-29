package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Map;

/**
 * 使用 RMap 操作 Redis 哈希
 * @author HuangKai
 */
public class MapExamples {
    private static final String  NODEADDRESS_M1 = "redis://10.254.13.92:6379";
    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        // RMap 继承了 java.util.concurrent.ConcurrentMap 接口
        RMap<String, String> map = client.getMap("c2sso:c2SsoToken:eyJraWQiOiJNdzVoSHE3MVF3Szh3RkdIaFRuR0pnIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJ1aWQiOiJhZG1pbiIsImFjIjoiYWRtaW4iLCJpc3MiOiJjMiIsInVuIjoi6LaF57qn566h55CG5ZGYIiwib3JnaW5zaWRzIjpbXSwib2lkIjpbXSwiZXhwIjoxNjA0MzA5MTIyLCJhaWQiOiIwIiwicm8iOlsiZGVmYXVsdCJdLCJpYXQiOjE2MDM3MDQzMjIsImNpZCI6W119.krz1x2E52gSVOWwFrI9Z6wU-SHGNUzvbUSUwWVWE28bxGEMQ6gFY1JDawWRfBQtJDejgobCcnX1HBiBscf5Uh4AYotohe_gt8nE4neeE_9Jr5Yxe4hwIXYMB-Po_MqNbQKSEU9HVKyWdr8_gd85q4NVs8GdjgzjwDUhNo0vI6jE");

        long time = map.remainTimeToLive();

        for(Map.Entry<String, String> entity : map.entrySet()){
            System.out.println("键值对， key: " + entity.getKey() +" , value :" + entity.getValue());
        }


//        map.put("name", "huangkai");
//        map.put("address", "changsha");
//        map.put("link", "https://github.com/hkdijia");
//        boolean contains = map.containsKey("link");
//        System.out.println("Map size: " + map.size());
//        System.out.println("Is map contains key 'link': " + contains);
//        String value = map.get("name");
//        System.out.println("Value mapped by key 'name': " + value);
//        boolean added = map.putIfAbsent("link", "https://doocs.github.io") == null;
//        System.out.println("Is value mapped by key 'link' added: " + added);
        client.shutdown();
    }
}
