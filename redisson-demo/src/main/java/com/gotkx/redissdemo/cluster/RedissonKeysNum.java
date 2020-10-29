package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RedissonKeysNum {

    private static final String NODEADDRESS_M1 = "redis://192.168.66.130:6380";

    private static AtomicInteger count = new AtomicInteger();

    private static Set<String> set = new HashSet<>();

    public static String getLoginAccountLockCacheKey(String account) {
        return String.format("c2sso:c2smsPhoneCount:%s", account);
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);
        //RSet<String> rSet = client.getSet("rr:*");

//        RKeys keys = client.getKeys();
//        Iterable<String> keysKeys = keys.getKeys();
//        for (String key : keysKeys) {
//            System.out.println(key);
//        }
//        boolean flag = false;

//        for (String key : client.getKeys().getKeys()) {
//            if(key.contains("rr")){
//                System.out.println("就是这个key :" + key);
//                flag = true;
//                break;
//            }

//        }

//        Set<String> collect = client.getKeys().getKeysStream().collect(Collectors.toSet());
//        for (String s : collect) {
//            System.out.println("测试： " + s);
//            count.incrementAndGet();
//        }
        String s1 = getLoginAccountLockCacheKey("");


        Set<String> set = client.getKeys().getKeysStream()
                //filter(key -> key.contains(s1))
                .filter(key -> key.startsWith(s1))
                .collect(Collectors.toSet());

        for (String s : set) {
            System.out.println("测试： " + s);
            count.incrementAndGet();
        }

//        RSet<String> rSet = client.getSet("rr:4:default");
//        for (String o : rSet) {
//            System.out.println("测试内容： " + o );
//            count.incrementAndGet();
//            set.add(o);
//        }
//
//
//        RSet<String> rSet2 = client.getSet("rr:4:amp_roleManager");
//        for (String o : rSet2) {
//            System.out.println("测试内容： " + o );
//            count.incrementAndGet();
//            set.add(o);
//        }
//
//
//        RSet<String> rSet3 = client.getSet("rr:4:amp_admin");
//        for (String o : rSet3) {
//            System.out.println("测试内容： " + o );
//            count.incrementAndGet();
//            set.add(o);
//        }
//
//
////        System.out.println("标志词： " + flag);
//        System.out.println("总共的键：" + count);
//
//        for (String s : set) {
//            System.out.println("打印set ：" + s);
//        }
//
//        System.out.println("测试set的大小:" + set.size());

        client.shutdown();
    }
}
