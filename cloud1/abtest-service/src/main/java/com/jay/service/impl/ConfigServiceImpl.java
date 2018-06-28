package com.jay.service.impl;

import com.jay.bean.ConfigItem;
import com.jay.dao.ConfigItemDao;
import com.jay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigItemDao configurationRepo;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String getString(String key, String... defaultValue) {
        String value = getConfigByAppAndKey("", key);
        if (StringUtils.isEmpty(value) && defaultValue.length > 0) {
            return defaultValue[0];
        }
        return value;
    }

    @Override
    public String getStringByApp(String app, String key, String... defaultValue) {
        String value = getConfigByAppAndKey(app, key);
        if (StringUtils.isEmpty(value) && defaultValue.length > 0) {
            return defaultValue[0];
        }
        return value;
    }

    @Override
    public boolean getBoolean(String key, boolean... defaultValue) {
        String value = getString(key, defaultValue.length > 0 ? String.valueOf(defaultValue[0]) : "false").toLowerCase();
        return value.equals("true") || value.equals("t") || value.equals("1");
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        String value = getString(key);
        return !StringUtils.isEmpty(value) ? new BigDecimal(value) : null;
    }

    @Override
    public int getInt(String key, int... defaultValue) {
        String value = getString(key, defaultValue.length > 0 ? String.valueOf(defaultValue[0]) : "NoConfigItem").toLowerCase();
        return Integer.parseInt(value);
    }

    @Override
    public long getLong(String key, long... defaultValue) {
        String value = getString(key, defaultValue.length > 0 ? String.valueOf(defaultValue[0]) : "NoConfigItem").toLowerCase();
        return Long.parseLong(value);
    }

    @Override
    public void update(String key, String value) {
        ConfigItem configuration = configurationRepo.getByCfgKey(key);
        configuration.setCfgValue(value);
        configurationRepo.save(configuration);

        String cacheKey = String.format("cfg:%s:%s", "", configuration.getCfgKey());
        redisTemplate.delete(cacheKey);
    }

    private String getConfigByAppAndKey(String app, String key) {
//        String cacheKey = String.format("cfg:%s:%s", app, key);
//        String value = redisTemplate.opsForValue().get(cacheKey);
//        if (!StringUtils.isEmpty(value)) {
//            return value;
//        }
        ConfigItem configuration = configurationRepo.getByCfgKey(key);
//        if (configuration != null) {
//            redisTemplate.opsForValue().set(cacheKey, configuration.getCfgValue(), 60 * 60);
//        }
        return configuration == null ? null : configuration.getCfgValue();
    }

    @Override
    public void insert(ConfigItem item) {
        configurationRepo.save(item);
    }

    @Override
    public List<ConfigItem> findAll() {
        return configurationRepo.findAll();
    }
}
