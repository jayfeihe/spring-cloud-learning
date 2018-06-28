package com.jay.service;

import com.jay.abtest.AbTestConfig;
import com.jay.bean.AbTest;

import java.io.Serializable;

public interface AbTestApi {

    public void insert(AbTest abTest);

    public String determine(AbTestConfig abTestConfig, Serializable testKey);

    public String determine(AbTestConfig abTestConfig, Serializable testKey, boolean isStore);

    /**
     * 确定 ab test 各选项比例
     *
     * @param testName      ab test 名称
     * @param testKey       确定ab test的依据
     * @param choices       ab test 所有选项
     * @param probabilities ab test 所有选项的概率, 会根据整数和自动计算
     * @return
     */
    public String determine(String testName, Serializable testKey, String[] choices, int[] probabilities);

    public String determine(String testName, Serializable testKey, String[] choices, int[] probabilities, boolean isStore);

    /**
     * 获取key ab test的选项, 没有返回 null
     *
     * @param testName
     * @param testKey
     * @return
     */
    public String get(String testName, Serializable testKey);

    /**
     * 校验AbTest结果
     *
     * @param abTestConfig
     * @param testKey
     * @return
     */
    public boolean check(AbTestConfig abTestConfig, Serializable testKey);

    /**
     * 校验AbTest结果
     *
     * @param abTestConfig
     * @param testKey
     * @param defaultConfig 默认配置(兼容老数据)
     * @return
     */
    public boolean check(AbTestConfig abTestConfig, Serializable testKey, AbTestConfig defaultConfig);

}
