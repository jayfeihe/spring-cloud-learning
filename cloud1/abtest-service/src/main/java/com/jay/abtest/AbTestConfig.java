package com.jay.abtest;

public abstract class AbTestConfig {
    /**
     * 定义abTest名称
     */
    public abstract String getTestName();

    /**
     * 定义AbTest中可选值
     *
     * @return
     */
    public abstract String[] getChoices();

    /**
     * 定义AbTest中每个test值选中比例
     *
     * 比例为百分比，可通过配置文件、redis、或MySQL配置(推荐：提供比例配置一个页面)
     *
     * @return
     */
    public abstract int[] getProbabilities();

    /**
     * 是否匹配给定的test值
     *
     * @param value
     * @return
     */
    public abstract boolean testChoice(String value);
}
