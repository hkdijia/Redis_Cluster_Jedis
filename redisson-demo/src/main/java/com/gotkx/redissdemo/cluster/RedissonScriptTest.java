package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RFuture;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RedissonScriptTest {

    private static final String NODEADDRESS_M1 = "redis://10.254.9.205:6380";
    private static String AIP_PERMISSION_VERIFY_SHA;
    private static final String AIP_PERMISSION_VERIFY_SCRIPT =
            new StringBuilder("return redis.call('get', 'foo{smd}') ").toString();

    private static Object o = new Object();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient redisson = Redisson.create(config);

        redisson.getBucket("foo{smd}").set("bar");
        String r = redisson.getScript()
                .eval(RScript.Mode.READ_ONLY,
                        "return redis.call('get', 'foo{smd}')",
                        RScript.ReturnType.VALUE
                );

        System.out.println("测试lua：" + r);

        if (AIP_PERMISSION_VERIFY_SHA == null) {
            AIP_PERMISSION_VERIFY_SHA = redisson.getScript().scriptLoad(AIP_PERMISSION_VERIFY_SCRIPT);
        }

        RFuture<Object> async = redisson.getScript().evalShaAsync(
                RScript.Mode.READ_ONLY,
                AIP_PERMISSION_VERIFY_SHA,
                RScript.ReturnType.VALUE,
                Collections.emptyList()
        );

        o = async.get();


        System.out.println("打印取得的值：" + o);

        redisson.shutdown();
    }


}
