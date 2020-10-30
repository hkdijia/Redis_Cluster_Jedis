package com.gotkx.jedisdemo.cluster.script;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

class JedisClusterPlus extends JedisCluster {

    public JedisClusterPlus(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, final GenericObjectPoolConfig poolConfig) {
        super(jedisClusterNode);
        super.connectionHandler = new JedisSlotAdvancedConnectionHandler(jedisClusterNode, poolConfig,
                connectionTimeout, soTimeout);
    }

    public JedisSlotAdvancedConnectionHandler getConnectionHandler() {
        return (JedisSlotAdvancedConnectionHandler)this.connectionHandler;
    }
}