package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 读取远程git中的配置文件
 *   http访问配置文件：
 *      http://localhost:1112/zuul-dev.properties
 *      或
 *      http://localhost:1112/zuul/dev
 *   http请求地址和资源文件映射如下:
 *      /{application}/{profile}[/{label}]
 *      /{application}-{profile}.yml
 *      /{label}/{application}-{profile}.yml
 *      /{application}-{profile}.properties
 *      /{label}/{application}-{profile}.properties
 *
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
