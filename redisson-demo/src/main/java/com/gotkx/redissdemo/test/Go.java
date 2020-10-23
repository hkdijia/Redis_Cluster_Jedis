package com.gotkx.redissdemo.test;

public class Go {

    public static void main(String[] args) {
        String sign = "TENCENT";
        ChannelRuleEnum channelRule = ChannelRuleEnum.match(sign);
        GeneralChannelRule rule = channelRule.channel;
        rule.process(sign);
    }
}
