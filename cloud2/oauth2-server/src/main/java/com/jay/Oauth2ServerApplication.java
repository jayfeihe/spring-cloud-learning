package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Redis存储Oauth2的授权Token
 *
 */

/**
 * 参考：
 *      https://github.com/lexburner/oauth2-demo
 *      https://www.jianshu.com/p/4089c9cc2dfd
 *
 * token获取：
 *
 * password模式：
 *      http://localhost:20005/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
 *      返回：
 *          {
 *              access_token: "04cabd40-a64c-4f1a-bb47-eb11f83d8ec6",
 *              token_type: "bearer",
 *              refresh_token: "16971dcf-0141-4fa0-a7c9-b518437425b6",
 *              expires_in: 41786,
 *              scope: "select"
 *          }
 *          说明：
 *              1.访问授权资源，携带access_token参数进行访问
 *              2.refresh_token  在刷新token是使用
 * client模式：
 *      http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
 *
 * 访问资源：
 *      在配置中，我们已经配置了对order资源的保护，如果直接访问：
 *      http://localhost:8080/order/1 会得到这样的响应：
 *      {"error":"unauthorized","error_description":"Full authentication is required to access this resource"} （这样的错误响应可以通过重写配置来修改） 而对于未受保护的product资源 http://localhost:8080/product/1 则可以直接访问，得到响应 product id : 1
 *
 *      携带accessToken参数访问受保护的资源： 使用password模式获得的token: http://localhost:8080/order/1?access_token=950a7cc9-5a8a-42c9-a693-40e817b1a4b0 得到了之前匿名访问无法获取的资源： order id :
 *
 *
 * token刷新：
 *      默认情况下，client模式不支持刷新token，password模式支持刷新token
 *      刷新token的端点为：
 *      http://localhost:20005/oauth/token?grant_type=refresh_token&refresh_token=your_refresh_token&client_id=client_2&client_secret=123456
 *      说明：
 *          1.your_refresh_token为通过password获取token时返回的refresh_token内容
 *          2.调用刷新token后，需要拿新的access_token进行资源访问，旧的失效
 *
 */
@SpringBootApplication
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}
