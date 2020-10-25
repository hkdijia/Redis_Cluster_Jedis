package com.gotkx.redissdemo.cluster;

import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

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


        redisson.getBucket("foo{smd}").set("bar");
        String r = redisson.getScript()
                            .eval(RScript.Mode.READ_ONLY,
                            "return redis.call('get', 'foo{smd}')",
                                    RScript.ReturnType.VALUE
                            );

        System.out.println("测试lua：" + r);

        // 通过预存的脚本进行同样的操作
        //RScript s = redisson.getScript();
        // 首先将脚本保存到所有的Redis主节点
        //String res = s.scriptLoad("return redis.call('get', 'foo')");
        // 返回值 res == 282297a0228f48cd3fc6a55de6316f31422f5d17

        List<Object> keys = new ArrayList<>();
        keys.add("rr:7nZS20cBSaarrNhxmF3Zqg:ggg");
        String resourceCodes = "youSee11";

        if(AIP_PERMISSION_VERIFY_SHA==null){
            AIP_PERMISSION_VERIFY_SHA = redisson.getScript().scriptLoad(AIP_PERMISSION_VERIFY_SCRIPT);
        }

        // 再通过SHA值调用脚本
        Future<Object> r1 = redisson.getScript().evalShaAsync(
                RScript.Mode.READ_WRITE,
                AIP_PERMISSION_VERIFY_SHA,
                RScript.ReturnType.MAPVALUELIST,
                keys,
                "youSee11");

//        Object eval = redisson.getScript().eval(
//                RScript.Mode.READ_ONLY, //执行模式
//                AIP_PERMISSION_VERIFY_SCRIPT,    // 要执行的lua脚本
//                RScript.ReturnType.MAPVALUE, // 返回值类型
//                keys,  // 传入KEYS
//                resourceCodes   //传入ARGV
//        );


        redisson.shutdown();
    }


}
