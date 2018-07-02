package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 特别注意：
 *  1.这里的AbTest中，暂不支持通过app区分
 *  2.正常配置项和比例，取出后，需要放入redis
 *      --缓存实现  getConfigByAppAndKey
 *
 *  3.提供了一个缓存统一操作的抽象封装    JayCache
 *    可自定义缓存失效，继承 JayChche，实现缓存存取和失效逻辑
 *          ---   一般用于，设置一批缓存内容时使用, 可在CommandLine接口中设置
 */
@SpringBootApplication
public class AbtestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbtestServiceApplication.class, args);
    }
}
