package com.jay.abtest;

import com.jay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 多个AbTest项可选，可设置比例
 */
@Component
public class MultiAbTestConfig extends AbTestConfig {

    //需要选填淘宝支付宝
    public final static String ALI_EXTAUTH_NEED = "need";
    //不需要选填淘宝支付宝
    public final static String ALI_EXTAUTH_NOT_NEED = "noNeed";

    //无芝麻
    public final static String ALI_EXTAUTH_NOT_NEED_ZHIMA = "noNeedZhima";
    //无芝麻、支付宝必填
    public final static String ALI_EXTAUTH_NEED_ALIPAY = "needAlipay";


    @Autowired
    private ConfigService configuration;

    @Override
    public String getTestName() {
        return "AliExtAuth";
    }

    //提供4个可选项
    @Override
    public String[] getChoices() {
        return new String[]{ALI_EXTAUTH_NEED, ALI_EXTAUTH_NOT_NEED, ALI_EXTAUTH_NOT_NEED_ZHIMA, ALI_EXTAUTH_NEED_ALIPAY};
    }

    //提供4个可选项比例值
    @Override
    public int[] getProbabilities() {
        return new int[]{configuration.getInt("AB_TEST_ALI_EXTAUTH_NEED"),
                configuration.getInt("AB_TEST_ALI_EXTAUTH_NOT_NEED"),
                configuration.getInt("AB_TEST_ALI_EXTAUTH_NOT_NEED_ZHIMA"),
                configuration.getInt("AB_TEST_ALI_EXTAUTH_NEED_ALIPAY")};
    }

    @Override
    public boolean testChoice(String value) {
        return value.equals(ALI_EXTAUTH_NEED);
    }
}
