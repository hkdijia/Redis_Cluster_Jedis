package com.gotkx.jedisdemo.utils;


import org.redisson.config.Config;

public class RedissonUtlis {

    Config config = new Config();
config.setUseLinuxNativeEpoll(true);
config.useClusterServers()
        .addNodeAddress("127.0.0.1:7181");

}
