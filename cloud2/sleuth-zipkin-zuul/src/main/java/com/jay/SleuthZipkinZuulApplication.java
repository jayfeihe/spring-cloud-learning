package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringCloudApplication
@EnableZuulProxy
public class SleuthZipkinZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthZipkinZuulApplication.class, args);
    }
}
