package com.gotkx.jedisdemo.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author HuangKai
 * jedisCluster连接redis（集群）
 */
public class ClusterJedis {

    public static String HOST = "192.168.50.21";
    public static int PORT = 6379;

    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        //Jedis Cluster will attempt to discover cluster nodes automatically
        jedisClusterNodes.add(new HostAndPort(HOST, PORT));
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        jc.set("ping", "pong");
        String value = jc.get("ping");
        System.out.println(value);
    }

}
