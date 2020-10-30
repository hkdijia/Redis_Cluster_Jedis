package com.gotkx.redissdemo.cluster;

import org.assertj.core.util.Lists;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonScript {

    private static final String NODEADDRESS_M1 = "redis://192.168.50.21:6380";

    private static String AIP_PERMISSION_VERIFY_SHA ;
    private static final String AIP_PERMISSION_VERIFY_SCRIPT = new StringBuilder("local keys = cjson.decode(KEYS[1]) ")
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

    public static void main(String[] args) {

        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(NODEADDRESS_M1);
        RedissonClient redisson = Redisson.create(config);


//        redisson.getBucket("foo{smd}").set("bar");
//        String r = redisson.getScript()
//                            .eval(RScript.Mode.READ_ONLY,
//                            "return redis.call('get', 'foo{smd}')",
//                                    RScript.ReturnType.VALUE
//                            );
//
//        System.out.println("测试lua：" + r);


        // 再通过SHA值调用脚本
        Object r1 = redisson.getScript().eval(
                RScript.Mode.READ_ONLY,
                AIP_PERMISSION_VERIFY_SCRIPT,
                RScript.ReturnType.MAPVALUE,
                Lists.newArrayList("rr:7nZS20cBSaarrNhxmF3Zqg:ggg","youSee11")
        );

        System.out.println(r1);

        redisson.shutdown();
    }


}
