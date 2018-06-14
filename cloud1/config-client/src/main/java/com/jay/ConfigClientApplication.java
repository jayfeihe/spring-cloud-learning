package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 对于设定访问的ConfigServer配置服务端的信息，必须是在bootstrap.yml或bootstrap.properties文件中配置，不然就会走默认的路径 http://localhost:8888
 */

/*
Spring Cloud Bus改造Config-client   ---   配置更新时，不用重启服务，即可更新

            0.启动rabbitMQ
                docker run -d --name rabbitmq --publish 5671:5671 \
                 --publish 5672:5672 --publish 4369:4369 --publish 25672:25672 --publish 15671:15671 --publish 15672:15672 \
                rabbitmq:management
            1.修改pom配置
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-actuator</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-aop</artifactId>
                </dependency>

            2.配置信息
                spring.rabbitmq.host=localhost
                spring.rabbitmq.port=5672
                #重点：在spring boot 2.0版本中一定要修改此配置，可以选择【"health","mappings","bus-refresh"】三种选项暴露那些端点，
                management.endpoints.web.exposure.include=*
                #开启总线消息更新功能
                spring.cloud.bus.refresh.enabled=true

                management:
                  endpoints:
                    web:
                      exposure:
                        include: bus-refresh
                      cors:
                        allowed-origins: "*"
                        allowed-methods: "*"

                #重点：在spring boot 2.0版本中一定要修改此配置，可以选择【"health","mappings","bus-refresh"】三种选项暴露那些端点，
                management.endpoints.web.exposure.include=*

            3.使用配置的地方添加注解    ---   在使用功能foo属性配置的Controller中，添加注解 @RefreshScope
                @RestController
                @RequestMapping("/config")
                @RefreshScope
                public class ConfigController {
                    @Value("${foo}")
                    String foo;

                    @RequestMapping(value = "/foo")
                    public String hi() {
                        return foo;
                    }
                }


            4.使用
                1.更新配置文件  config-client-dev.properties   foo属性
                2.POST请求
                    http://localhost:1113/actuator/bus-refresh
                3.客户端再次请求配置文件
                    看到配置文件已更新
 */
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
