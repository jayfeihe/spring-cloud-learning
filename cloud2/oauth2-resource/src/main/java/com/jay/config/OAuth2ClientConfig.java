package com.jay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * OAuth2的客户端配置
 * 3个配置：
 *  1.配置受保护的资源信息
 *  2.配置过滤器，存储当前请求和上下文
 *  3.在Request域内创建AccessTokenRequest类型的Bean
 */
@Configuration
@EnableConfigurationProperties
@EnableOAuth2Client
public class OAuth2ClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    /*
        用于向授权服务请求的OAuth2RestTemplate类型的Bean
     */
    @Bean
    public OAuth2RestTemplate clientCredentialsRestTmplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}
