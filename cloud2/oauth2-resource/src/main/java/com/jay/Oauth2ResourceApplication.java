package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 1.未获取token令牌时，/user/registry 可以访问  /hi访问受限
 *
 * 2.获取Token令牌
 *  http://localhost:5000/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
 *  返回：
 *      {"access_token":"f8cfbb72-8ce4-444c-8916-36d0f49d1708","token_type":"bearer","expires_in":43199,"scope":"select"}
 * 3.拿着令牌，访问受限的资源
 *  http://localhost:5001/hi?access_token=f8cfbb72-8ce4-444c-8916-36d0f49d1708
 *  返回正常数据
 *
 * 4.拿着令牌，访问授权资源  /hello    需要ROLE_ADMIN 角色
 *   角色不对，也无权访问   --   使用的时client_1，在授权服务中，client_1的角色是oauth2
 *
 *  http://localhost:5001/hello?access_token=f8cfbb72-8ce4-444c-8916-36d0f49d1708
 *      <oauth>
 *          <error_description>不允许访问</error_description>
 *          <error>access_denied</error>
 *      </oauth>
 *
 * 5.查看角色信息
 *  http://localhost:5001/getPrinciple?access_token=f8cfbb72-8ce4-444c-8916-36d0f49d1708
 *
 *
 */
@SpringBootApplication
public class Oauth2ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ResourceApplication.class, args);
    }
}
