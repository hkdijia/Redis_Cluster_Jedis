package com.gotkx.jedisdemo.standalone;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author HuangKai
 * jedis 连接redis（单机）
 */
public class StandaloneJedis {

    public static String ADDRESS = "127.0.0.1";

    private static final String RESOUCE_KEY_FORMAT = "rr:%s:%s";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
//        Jedis jedis = new Jedis(ADDRESS);
//        jedis.set("foo", "bar");
//        String value = jedis.get("foo");
//        System.out.println(value);

        ArrayList<String> list = new ArrayList<>();
        list.add("amp_admin");
        //list.add()
        Set<String> queryAll = queryAll("4", list);
    }


    @SuppressWarnings("unchecked")
    public static Set<String> queryAll(String appId, List<String> roleCodes){
        Jedis jedis = new Jedis(ADDRESS);
        List<String> keys = roleCodes.stream().map((roleCode)->{
            return String.format(RESOUCE_KEY_FORMAT, appId, roleCode);
        }).collect(Collectors.toList());

        return jedis.sunion(keys.toArray(new String[0]));
    }

}
