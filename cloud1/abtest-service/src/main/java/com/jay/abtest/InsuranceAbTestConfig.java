package com.jay.abtest;

import com.jay.abtest.AbTestConfig;
import com.jay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 是否买保险的AbTest分组
 */
@Component
public class InsuranceAbTestConfig extends AbTestConfig {

    //需要买保险
    public final static String INSURANCE_NEED = "need";
    //不需要买保险
    public final static String INSURANCE_NOT_NEED = "noNeed";

    //AbTest名称
    public final static String AB_TEST_NMAE = "insurance_ab_test";

    @Autowired
    private ConfigService configuration;

    @Override
    public String getTestName() {
        return AB_TEST_NMAE;
    }

    @Override
    public String[] getChoices() {
        return new String[]{INSURANCE_NEED, INSURANCE_NOT_NEED};
    }

    @Override
    public int[] getProbabilities() {
        return new int[]{configuration.getInt("AB_TEST_INSURANCE_NEED"),
                configuration.getInt("AB_TEST_INSURANCE_NOT_NEED")};
    }

    @Override
    public boolean testChoice(String value) {
        return value.equals(INSURANCE_NEED);
    }
}
