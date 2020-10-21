package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 RLock 实现 Redis 分布式锁
 * @author HuangKai
 */
public class LockExamples {

    private static final Logger logger =
            LoggerFactory.getLogger(LockExamples.class);


    private static final String  NODEADDRESS_M1 = "redis://192.168.50.21:6379";

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient client = Redisson.create(config);
        // RLock 继承了 java.util.concurrent.locks.Lock 接口
        RLock lock = client.getLock("lock");

        lock.lock();
        System.out.println("lock acquired");

        Thread t = new Thread(() -> {
            RLock lock1 = client.getLock("lock");
            lock1.lock();
            System.out.println("lock acquired by thread");
            lock1.unlock();
            System.out.println("lock released by thread");
        });

        t.start();
        t.join(1000);

        lock.unlock();
        System.out.println("lock released");

        t.join();

        client.shutdown();
    }
}
