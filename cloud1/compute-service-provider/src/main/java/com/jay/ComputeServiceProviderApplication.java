package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
public class ComputeServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputeServiceProviderApplication.class, args);
    }
}
