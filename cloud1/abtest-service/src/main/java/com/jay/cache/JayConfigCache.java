package com.jay.cache;

import com.jay.bean.ConfigItem;
import com.jay.dao.ConfigItemDao;
import com.jay.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JayConfigCache extends JayCache<String> {

    private final static String KEY = "JayConfigCache";

    @Autowired
    private ConfigItemDao configItemDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public JayConfigCache() {
        super(KEY);
    }

    @Override
    public void set(String key, String value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire());
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public int expire() {
        return CACHE_TIME_1_HR;
    }

    @Override
    public String getWhenNotExist() {
        //没有找到缓存，则先查库，查到后放入缓存
        List<ConfigItem> list = configItemDao.findAll();
        set(KEY, JsonUtils.toString(list), expire());
        return get(KEY);
    }
}
