package com.jay.service;


import com.jay.bean.ConfigItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用于配置AbTest可选值和内容的服务类    ---    AbTest可选值和比例，写入MySQL
 */
public interface ConfigService {

    public String getString(String key, String... defaultValue);

    public String getStringByApp(String app, String key, String... defaultValue);

    public boolean getBoolean(String key, boolean... defaultValue);

    public BigDecimal getBigDecimal(String key);

    public int getInt(String key, int... defaultValue);

    public long getLong(String key, long... defaultValue);

    void update(String key, String value);

    void insert(ConfigItem item);

    public List<ConfigItem> findAll();
}
