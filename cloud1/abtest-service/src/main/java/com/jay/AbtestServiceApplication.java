package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 特别注意：
 *  1.这里的AbTest中，暂不支持通过app区分
 *  2.正常配置项，取出后，需要放入redis，此处放redis有二进制问题，咱不支持redis缓存
 */
@SpringBootApplication
public class AbtestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbtestServiceApplication.class, args);
    }
}
