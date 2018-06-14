package com.jay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jay.service.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
/**
 * 基于MySQL存储Oauth2的授权Token，用户名和密码，存储在mysql中
 * 参考：
 *      https://www.jianshu.com/p/4089c9cc2dfd
 *
 */
/*
引入一些OAuth2相关的端点
包含以下的端点：

    AuthorizationEndpoint 根据用户认证获得授权码，有下面两个方法：
    /oauth/authorize - GET
    /oauth/authorize - POST

    TokenEndpoint 客户端根据授权码获取 token
    /oauth/token - GET
    /oauth/token - POST

    CheckTokenEndpoint 可以用于远程解码令牌
    /oauth/check_token

    WhitelabelApprovalEndpoint 显示授权服务器的确认页。
    /oauth/confirm_access

    WhitelabelErrorEndpoint 显示授权服务器的错误页
    /oauth/error

这些端点有个特点，如果你自己实现了上面的方法，他会优先使用你提供的方法，利用这个特点，通常都会根据自己的需要来设计自己的授权确认页面
 */

/**
 * 参考：
 *      http://blog.didispace.com/spring-security-oauth2-xjf-1/
 *
 * 该项目的/users下所有请求都需要授权后才能访问，
 * 其他请求地址，不需要授权即可访问
 */

/**
参考：
    https://github.com/lexburner/oauth2-demo


获取授权token

 1.使用password授权
      http://localhost:5000/oauth/token?username=test1&password=test1&grant_type=password&scope=select&client_id=client_2&client_secret=123456
        返回：
        {
            access_token: "10926f80-1ee0-4fdb-8a64-c4bb824f779d",
            token_type: "bearer",
            refresh_token: "a35cf825-0910-4515-8683-dc9b5655b463",
            expires_in: 42215,
            scope: "select"
        }
         说明：
               1.访问授权资源，携带access_token参数进行访问
               2.refresh_token  在刷新token是使用

 2.使用client授权
      http://localhost:5000/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
        {
            access_token: "56818a6a-527a-4f99-b585-3b950d746290",
            token_type: "bearer",
            expires_in: 17473,
            scope: "select"
        }

 访问资源：
 *      在配置中，我们已经配置了对users资源的保护，如果直接访问：
 *      http://localhost:5000/users/1 会得到这样的响应：
 *      {"error":"unauthorized","error_description":"Full authentication is required to access this resource"} （这样的错误响应可以通过重写配置来修改） 而对于未受保护的product资源 http://localhost:8080/product/1 则可以直接访问，得到响应 product id : 1
 *
 *      携带accessToken参数访问受保护的资源： 使用password模式获得的token: http://localhost:5000/users/findAll?access_token=950a7cc9-5a8a-42c9-a693-40e817b1a4b0 得到了之前匿名访问无法获取的资源： order id :
 *
 *
 * token刷新：
 *      默认情况下，client模式不支持刷新token，password模式支持刷新token
 *      刷新token的端点为：
 *      http://localhost:5000/oauth/token?grant_type=refresh_token&refresh_token=your_refresh_token&client_id=client_2&client_secret=123456
 *      说明：
 *          1.your_refresh_token为通过password获取token时返回的refresh_token内容
 *          2.调用刷新token后，需要拿新的access_token进行资源访问，旧的失效
 */
@SpringCloudApplication
public class Oauth2ProviderApplication {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }


    public static void main(String[] args) {
        SpringApplication.run(Oauth2ProviderApplication.class, args);
    }

//    @Autowired
//    @Qualifier("dataSource")
//    private DataSource dataSource;
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
//
//        private TokenStore tokenStore = new InMemoryTokenStore();
//
////        JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
////        @Autowired
////        private UserServiceDetail userServiceDetail;
//
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//            clients.inMemory()
//                    .withClient("browser")
//                    .authorizedGrantTypes("refresh_token", "password")
//                    .scopes("ui")
//                    .and()
//                    .withClient("service-hi")
//                    .secret("123456")
//                    .authorizedGrantTypes("client_credentials", "refresh_token", "password")
//                    .scopes("server");
//
//        }
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints
//                    .tokenStore(tokenStore)
//                    .authenticationManager(authenticationManager);
////                    .userDetailsService(userServiceDetail);
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//            oauthServer
//                    .tokenKeyAccess("permitAll()")
//                    .checkTokenAccess("isAuthenticated()");
//
//        }
//    }
}
