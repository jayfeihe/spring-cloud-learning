package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient  //注册Eureka
@EnableFeignClients  //开启feign支持
public class ZuulSaleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulSaleServiceApplication.class, args);
    }
}
