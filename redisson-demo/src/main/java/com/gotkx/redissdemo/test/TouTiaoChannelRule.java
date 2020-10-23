package com.gotkx.redissdemo.test;

public class TouTiaoChannelRule extends GeneralChannelRule {

    @Override
    public void process(String sign) {
        System.out.println("这是头条");
    }

}
