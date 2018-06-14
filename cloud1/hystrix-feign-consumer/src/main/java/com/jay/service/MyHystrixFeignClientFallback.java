package com.jay.service;

import org.springframework.stereotype.Component;

@Component
public class MyHystrixFeignClientFallback implements MyHystrixFeignClient {
    @Override
    public String findPerson(int id) {
        return "查询用户失败！";
    }
}
