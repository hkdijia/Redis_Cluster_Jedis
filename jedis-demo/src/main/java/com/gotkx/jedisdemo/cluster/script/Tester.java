package com.gotkx.jedisdemo.cluster.script;

import redis.clients.jedis.*;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;

public class Tester {
    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        HostAndPort hostAndPort1 = new HostAndPort("192.168.50.21",6379);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.50.140",6379);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.50.60",6379);
        jedisClusterNode.add(hostAndPort1);
        jedisClusterNode.add(hostAndPort2);
        jedisClusterNode.add(hostAndPort3);

        JedisClusterPlus jedisClusterPlus = new JedisClusterPlus(jedisClusterNode, 2000, 2000, new JedisPoolConfig());
        JedisSlotAdvancedConnectionHandler jedisSlotAdvancedConnectionHandler = jedisClusterPlus.getConnectionHandler();

        String[] testKeys = {"foo","bar","xyz"};

        Map<JedisPool, List<String>> poolKeys = new HashMap<>();

        for (String key : testKeys) {
            int slot = JedisClusterCRC16.getSlot(key);
            JedisPool jedisPool = jedisSlotAdvancedConnectionHandler.getJedisPoolFromSlot(slot);
            if (poolKeys.keySet().contains(jedisPool)){
                List<String> keys = poolKeys.get(jedisPool);
                keys.add(key);
            }else {
                List<String> keys = new ArrayList<>();
                keys.add(key);
                poolKeys.put(jedisPool, keys);
            }
        }

        for (JedisPool jedisPool : poolKeys.keySet()) {
            Jedis jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            List<String> keys = poolKeys.get(jedisPool);

            keys.forEach(key ->pipeline.get(key));

            List result = pipeline.syncAndReturnAll();

            System.out.println(result);

            jedis.close();
        }
    }
}