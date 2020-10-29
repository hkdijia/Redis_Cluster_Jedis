package com.gotkx.redissdemo.cluster;

import org.assertj.core.util.Lists;
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

    private static final String NODEADDRESS_M1 = "redis://192.168.66.130:6379";
    private static final String NODEADDRESS_S1 = "redis://192.168.66.130:6380";
    private static final String NODEADDRESS_M2 = "redis://192.168.66.131:6379";
    private static final String NODEADDRESS_S2 = "redis://192.168.66.131:6380";
    private static final String NODEADDRESS_M3 = "redis://192.168.66.132:6379";
    private static final String NODEADDRESS_S3 = "redis://192.168.66.132:6380";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_S3)
                .addNodeAddress(NODEADDRESS_M3)
                .addNodeAddress(NODEADDRESS_S2)
                .addNodeAddress(NODEADDRESS_M2)
                .addNodeAddress(NODEADDRESS_S1)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient redisson = Redisson.create(config);

        //redisson.getBucket("foo{smd}").set("bar");
//        String r = redisson.getScript()
//                .eval(RScript.Mode.READ_ONLY,
//                        "return redis.call('get', 'foo{smd}')",
//                        RScript.ReturnType.VALUE
//                );
//
//        System.out.println("测试lua：" + r);
//
//        if (AIP_PERMISSION_VERIFY_SHA == null) {
//            AIP_PERMISSION_VERIFY_SHA = redisson.getScript().scriptLoad(AIP_PERMISSION_VERIFY_SCRIPT);
//        }
//
//        RFuture<Object> async = redisson.getScript().evalShaAsync(
//                RScript.Mode.READ_ONLY,
//                AIP_PERMISSION_VERIFY_SHA,
//                RScript.ReturnType.VALUE,
//                Collections.emptyList()
//        );
//
//        o = async.get();
//        System.out.println("打印取得的值：" + o);

        // 通过预存的脚本进行同样的操作
        RScript s = redisson.getScript();
        // 首先将脚本保存到所有的Redis主节点
        String res = s.scriptLoad("return redis.call('get', foo{smd})");
       //  返回值 res == 282297a0228f48cd3fc6a55de6316f31422f5d17

        // 再通过SHA值调用脚本
//        Future<Object> r1 = redisson.getScript().evalShaAsync(RScript.Mode.READ_ONLY,
//                res,
//                RScript.ReturnType.STATUS, Collections.emptyList());

        Object foo = redisson.getScript().evalShaAsync(
                RScript.Mode.READ_ONLY, //执行模式
                res,
                RScript.ReturnType.VALUE,
                Collections.emptyList()
                //Lists.newArrayList("foo{smd}")
        );


        System.out.println("jjjjjjjjj");
        redisson.shutdown();
    }


}
