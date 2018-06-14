package com.jay.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyHystrixFeignClientFallbackFactory implements FallbackFactory<MyHystrixFeignClient> {
    @Override
    public MyHystrixFeignClient create(Throwable throwable) {
        return new MyHystrixFeignClient() {
            @Override
            public String findPerson(int id) {
                log.error("异常处理={}", throwable);
                return "查询用户失败！Execute raw fallback: access service fail , req= " + id + " reason = " + throwable;
            }
        };
    }
}
