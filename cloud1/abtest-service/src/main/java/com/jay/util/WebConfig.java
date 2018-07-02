package com.jay.util;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

public class WebConfig {

    private static Properties appConfig = new Properties();

    static {
        try {
            appConfig.load(WebConfig.class.getResourceAsStream("/appConfig.properties"));
        } catch (Exception e) {
            throw new RuntimeException("无法读取 appConfig.properties 报错");
        }
    }

    public static boolean isProd() {
        return getRuntimeEnv().equals("prod")
                || getRuntimeEnv().equals("gray2")
                || getRuntimeEnv().equals("gray3");
    }

    public static String getRuntimeEnv() {
        return getString("runtime.env", "test1");
    }

    protected static String getString(String key, String defaultValue) {
        String value = getString(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    public static String getString(String key) {
        String value = appConfig.getProperty(key);

        //从外部配置文件或环境变量读取
//        if (StringUtils.isBlank(value)) {
//            value = BaseConfig.getString(key, null);
//        }
//        if (StringUtils.isBlank(value)) {
//            value = BaseConfig.getEnvString(key, null);
//        }
        return value;
    }
}
