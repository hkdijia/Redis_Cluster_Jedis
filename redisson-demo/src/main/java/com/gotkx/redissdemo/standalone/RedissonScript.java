package com.gotkx.redissdemo.standalone;

import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RedissonScript {

    private static final String NODEADDRESS_M1 = "redis://10.254.12.9:6379";

    private static Object o = new Object();

    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.254.12.9:6379");
        RedissonClient redisson = Redisson.create(config);

        redisson.getBucket("foo").set("bar");
        String r = redisson.getScript().eval(RScript.Mode.READ_ONLY,
                "return redis.call('get', 'foo')", RScript.ReturnType.VALUE);

        // 通过预存的脚本进行同样的操作
        RScript s = redisson.getScript();
        // 首先将脚本保存到所有的Redis主节点
        String res = s.scriptLoad("return redis.call('get', 'foo')");
        // 返回值 res == 282297a0228f48cd3fc6a55de6316f31422f5d17

        // 再通过SHA值调用脚本
        Future<Object> r1 = redisson.getScript().evalShaAsync(RScript.Mode.READ_ONLY,
                "282297a0228f48cd3fc6a55de6316f31422f5d17",
                RScript.ReturnType.VALUE, Collections.emptyList());

        try {
            o = r1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("打印取得的值：" + o);
        redisson.shutdown();
    }


}
