package com.jay.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 通过 Feign调用服务接口
 */
@FeignClient(name = "compute-service",
        fallbackFactory = MyHystrixFeignClientFallbackFactory.class
//        fallback =  MyHystrixFeignClientFallback.class
)
public interface MyHystrixFeignClient {

    @GetMapping("/compute/person/{id}")
    public String findPerson(@PathVariable("id") int id);
}
