//package com.gotkx.redissdemo.cluster;
//
//import org.redisson.api.RAtomicLong;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RedissonConfig {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Bean
//    public RedissonLocker redissonLocker(){
//
//        RedissonLocker redissonLocker = new RedissonLocker(redissonClient);
//        RAtomicLong redisson = redissonClient.getAtomicLong("redisson_test_key");
//        redisson.set(1000);
//
//        long l = redisson.get();
//        System.out.println("初始值  "+ l);
//        LockerUtil.setLocker(redissonLocker);
//
//        return redissonLocker;
//    }
//}
