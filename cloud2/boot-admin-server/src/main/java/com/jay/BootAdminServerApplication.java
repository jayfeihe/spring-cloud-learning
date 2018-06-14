package com.jay;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 依赖  eurka-sever, sleuth-zipkin-client
 *
 * 登录：
 *  root
 *  root
 */
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class BootAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootAdminServerApplication.class, args);
    }
}
