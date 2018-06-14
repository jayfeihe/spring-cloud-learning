package com.jay;

import com.jay.filter.ZuulAccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 参考：
 * https://www.jianshu.com/p/852b8f7b4358
 */
@SpringCloudApplication
@EnableZuulProxy
public class ApiZuulGatewayApplication {

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ZuulAccessFilter accessFilter() {
        ZuulAccessFilter filter = new ZuulAccessFilter();
        filter.setRestTemplate(getRestTemplate());
        return filter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiZuulGatewayApplication.class, args);
    }
}
