package com.jay.service;

import com.jay.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//声明要调用的服务名称
@FeignClient(value = "COMPUTE-SERVICE", configuration = FeignConfig.class, fallback = ComputeClientHystrix.class)
public interface ComputeClient {


    /**
     * 这里的value是，提供者提供的，访问接口的全路径
     */
    @RequestMapping(method = RequestMethod.GET, value = "/compute/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

    /**
     * 实际调用地址   serverId/compute/person/{id}
     */
    @GetMapping("/compute/person/{id}")
    String findPerson(@PathVariable("id") Integer id);
}
