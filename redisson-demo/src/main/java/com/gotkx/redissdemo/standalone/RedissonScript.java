package com.gotkx.redissdemo.standalone;

import org.assertj.core.util.Lists;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonScript {

    private static Object o = new Object();
    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.50.191:6379");
        RedissonClient redisson = Redisson.create(config);

//        redisson.getBucket("foo").set("bar");
//        String r = redisson.getScript().eval(RScript.Mode.READ_ONLY,
//                "return redis.call('get', 'foo')", RScript.ReturnType.VALUE);

        // 通过预存的脚本进行同样的操作
        //RScript s = redisson.getScript();
        // 首先将脚本保存到所有的Redis主节点
        String res = redisson.getScript().scriptLoad("return redis.call('get', KEYS[1])");

        Object r1 = redisson.getScript().evalShaAsync(RScript.Mode.READ_ONLY,
                res,
                RScript.ReturnType.VALUE, Lists.newArrayList("foo"));


        System.out.println(r1);

//        try {
//            o = r1.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


//        Object foo = redisson.getScript().eval(
//                RScript.Mode.READ_ONLY, //执行模式
//                "return redis.call('get', KEYS[1])",    // 要执行的lua脚本
//                RScript.ReturnType.VALUE, // 返回值类型
//                Lists.newArrayList("foo")
//        );

        ///System.out.println("打印取得的值：" + foo);
        redisson.shutdown();
    }


}
