package com.jay.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignConfig {

    /**
     * 第一个参数period是请求重试的间隔算法参数，第二个参数maxPeriod 是请求间隔最大时间，第三个参数是重试的次数
     */
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
