package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 读取本地配置文件，提供给config客户端
 * 访问配置文件：
 *   eg:
 *      http://localhost:1114/config-client/dev
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
public class ConfigServerNativeLocalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerNativeLocalApplication.class, args);
    }
}
