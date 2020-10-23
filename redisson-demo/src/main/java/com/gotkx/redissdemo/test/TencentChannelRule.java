package com.gotkx.redissdemo.test;

public class TencentChannelRule extends GeneralChannelRule {

    @Override
    public void process(String sign) {
        System.out.println("这是腾讯");
    }
}
