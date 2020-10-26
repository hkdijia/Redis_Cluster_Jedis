
package com.gotkx.redissdemo.cluster;

import org.assertj.core.util.Lists;
import org.redisson.Redisson;
import org.redisson.api.RFuture;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class RedissonScriptTest2 {
    private static final String NODEADDRESS_M1 = "redis://10.254.9.205:6379";
    private static String AIP_PERMISSION_VERIFY_SHA;
    private static final String AIP_PERMISSION_VERIFY_SCRIPT =
            new StringBuilder("return redis.call('get', 'KEYS[1]') ").toString();
    private static Object o = new Object();

    private static final String AIP_PERMISSION_VERIFY_SCRIPT_2 = new StringBuilder("local keys = cjson.decode(KEYS[1]) ")
            .append("local resIds = cjson.decode(KEYS[2]) ")
            .append("local result = {} ")
            .append("for resIndex,resValue in ipairs(resIds) do ")
            .append("     result[resValue] = false ")
            .append("end ")
            .append("for keyIndex,keyValue in ipairs(keys) do ")
            .append("      for resIndex,resValue in ipairs(resIds) do ")
            .append("         if result[resValue]==false then ")
            .append("                result[resValue] = (redis.call('sismember',keyValue,resValue)==1) ")
            .append("          end ")
            .append("      end ")
            .append("end ")
            .append("return cjson.encode(result)").toString();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(3000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient redisson = Redisson.create(config);
        //redisson.getBucket("foosmd").set("bar");
        if (AIP_PERMISSION_VERIFY_SHA == null) {
            AIP_PERMISSION_VERIFY_SHA = redisson.getScript().scriptLoad(AIP_PERMISSION_VERIFY_SCRIPT);
        }
//        RFuture<Object> async = redisson.getScript().evalShaAsync(
//                RScript.Mode.READ_ONLY,
//                AIP_PERMISSION_VERIFY_SHA,
//                RScript.ReturnType.VALUE,
//                Lists.newArrayList("foosmd"),
//                true
//        );

        Object async = redisson.getScript().evalSha(
                RScript.Mode.READ_ONLY,
                AIP_PERMISSION_VERIFY_SHA,
                RScript.ReturnType.VALUE,
                Lists.newArrayList("foosmd")
        );


        System.out.println("打印取得的值：" + async);
        redisson.shutdown();
    }


}
