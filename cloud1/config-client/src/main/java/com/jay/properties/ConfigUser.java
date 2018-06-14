package com.jay.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*
读取远程git配置文件中属性
 */
@Component
@ConfigurationProperties(prefix = "config.jay")
@Data
public class ConfigUser {
//    @Value("${config.jay.name}")
    private String name;
//    @Value("${config.jay.password}")
    private String password;
//    @Value("${config.jay.address}")
    private String address;
//    @Value("${config.jay.age}")
    private int age;
}
