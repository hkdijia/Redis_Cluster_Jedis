package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 使用 RList 操作 Redis 列表
 * @author HuangKai
 */
public class ListExamples {

    private static final String  NODEADDRESS_M1 = "redis://10.254.13.20:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        // RList 继承了 java.util.List 接口
        RList<String> nameList = client.getList("nameList");
        nameList.clear();
        nameList.add("smd");
        nameList.add("kualakualua");
        nameList.add("http://www.gotkx.com");
        //nameList.remove(-1);

        boolean contains = nameList.contains("smd");
        System.out.println("List size: " + nameList.size());
        System.out.println("Is list contains name 'smd': " + contains);
        nameList.forEach(System.out::println);
        client.shutdown();
    }
}
