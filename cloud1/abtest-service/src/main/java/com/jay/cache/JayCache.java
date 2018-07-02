package com.jay.cache;

import java.io.Serializable;

import jodd.util.ObjectUtil;

public abstract class JayCache<T extends Serializable> {

    public static final int CACHE_TIME_1_MIN = 1 * 60;
    public static final int CACHE_TIME_5_MIN = 5 * 60;
    public static final int CACHE_TIME_30_MIN = 30 * 60;
    public static final int CACHE_TIME_1_HR = 60 * 60;
    public static final int CACHE_TIME_12_HR = 12 * 60 * 60;
    public static final int CACHE_TIME_1_DAY = 24 * 60 * 60;
    public static final int CACHE_TIME_7_DAY = 7 * 24 * 60 * 60;
    public static final int CACHE_TIME_1_MON = 30 * 24 * 60 * 60;

    protected Serializable key;

    public JayCache(Serializable key) {
        this.key = key;
    }

    public T get() {
        try {

            String keyWrapper = String.format("cache:%s", key.toString());
            T t = get(keyWrapper);
            if (t == null) {
                t = getWhenNotExist();
                set(keyWrapper, t, expire());
                return t;
            } else {
                return t;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void set(String key, T value, long expire);

    public abstract T get(String key);

    /**
     * 缓存时间, 单位秒
     *
     * @return
     */
    public abstract int expire();

    public abstract T getWhenNotExist();

}
