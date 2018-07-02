package com.jay.controller;

import com.jay.cache.JayConfigCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试：
 *  http://localhost:10011/cache/get/jayConfig
 * 解析：
 *  第一次获取时，redis中不存在，先查数据库，查出结果，放入redis，以后只要不过期，就直接从缓存取
 *  缓存过期失效后，通过JayCache中的 getWhenNotExist()实现，重新加载数据到缓存
 *
 *
 */
@RestController
@RequestMapping("/cache")
public class JayCacheController {

    @Autowired
    private JayConfigCache jayConfigCache;

    @GetMapping("/get/jayConfig")
    public String getCahce(){
        return jayConfigCache.get();
    }
}
