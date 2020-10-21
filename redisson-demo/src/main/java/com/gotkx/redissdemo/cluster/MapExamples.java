package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 使用 RMap 操作 Redis 哈希
 * @author HuangKai
 */
public class MapExamples {

    private static final String  NODEADDRESS_M1 = "redis://192.168.50.21:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        // RMap 继承了 java.util.concurrent.ConcurrentMap 接口
        RMap<String, String> map = client.getMap("personalInfo");
        map.put("name", "huangkai");
        map.put("address", "changsha");
        map.put("link", "https://github.com/hkdijia");

        boolean contains = map.containsKey("link");
        System.out.println("Map size: " + map.size());
        System.out.println("Is map contains key 'link': " + contains);
        String value = map.get("name");
        System.out.println("Value mapped by key 'name': " + value);
        boolean added = map.putIfAbsent("link", "https://doocs.github.io") == null;
        System.out.println("Is value mapped by key 'link' added: " + added);
        client.shutdown();
    }
}
