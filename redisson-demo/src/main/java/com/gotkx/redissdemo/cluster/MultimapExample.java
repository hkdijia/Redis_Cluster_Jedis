package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 基于Set的Multimap不允许一个字段值包含有重复的元素。
 */
public class MultimapExample {

    private static final String  NODEADDRESS_M1 = "redis://10.254.13.20:6379";

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);

        RListMultimap<String, String> map = client.getListMultimap("kkkkkkkkkk");
        map.put(new String("0"), new String("1"));
        map.put(new String("0"), new String("2"));
        map.put(new String("0"), new String("1"));
        map.put(new String("3"), new String("4"));

//        List<String> allValues = map.get(new String("0"));
//        Collection<String> newValues = Arrays.asList(new String("7"), new String("6"), new String("5"));
//        List<String> oldValues = map.replaceValues(new String("0"), newValues);
//
//        List<String> removedValues = map.removeAll(new String("0"));

        client.shutdown();
    }

}
