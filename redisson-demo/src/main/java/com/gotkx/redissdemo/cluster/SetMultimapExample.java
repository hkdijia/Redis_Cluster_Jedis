package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RListMultimap;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 基于List的Multimap在保持插入顺序的同时允许一个字段下包含重复的元素。
 */
public class SetMultimapExample {

    private static final String  NODEADDRESS_M1 = "redis://10.254.13.20:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        RSetMultimap<String, String> map = client.getSetMultimap("myMultimap");
        map.put(new String("0"), new String("1"));
        map.put(new String("0"), new String("2"));
        map.put(new String("3"), new String("4"));

//        Set<String> allValues = map.get(new String("0"));
//        List<String> newValues = Arrays.asList(new String("7"), new String("6"), new String("5"));
//        Set<String> oldValues = map.replaceValues(new String("0"), newValues);
//        Set<String> removedValues = map.removeAll(new String("0"));

        client.shutdown();
    }

}
