package com.jay;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Spring Boot Admin 集成 Turbine
 *
 * 依赖：
 *    eurka-sever, hystrix-demo1, hystrix-demo1, turbine-service
 *
 */
@SpringCloudApplication
@EnableAdminServer
@EnableHystrixDashboard
@EnableTurbine
public class BootAdminTurbineServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootAdminTurbineServerApplication.class, args);
    }
}
