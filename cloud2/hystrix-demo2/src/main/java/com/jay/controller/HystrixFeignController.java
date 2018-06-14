package com.jay.controller;

import com.jay.service.MyHystrixFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix/feign")
public class HystrixFeignController {

    @Autowired
    private MyHystrixFeignClient hystrixFeignClient;

    /**
     * 测试Hystrix功能方法：
     *     1.Hystrix默认超时时间是1秒，可以在被调用接口中，返回休眠1.5秒    ---   超时后，hystrixFeignClient返回fallback
     *     2.停掉被调用服务，调用失败，hystrixFeignClient也会走fallback失败方法
     */
    @GetMapping("/person/{id}")
    public String findPerson(@PathVariable("id") int id) {
        return hystrixFeignClient.findPerson(id);
    }

}
