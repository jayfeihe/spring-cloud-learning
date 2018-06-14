package com.jay;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

/*
访问：
    http://localhost:20001/hystrix/ribbon/person/1
    http://localhost:20003/hystrix/ribbon/person/1

访问仪表盘：
    单个应用  hystrix-demo1  hystrix-demo2
        http://localhost:20001/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A20001%2Fhystrix.stream
        http://localhost:20003/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A20003%2Fhystrix.stream
    turbine应用
        http://localhost:20002/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A20002%2Fturbine.stream

 */
@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
public class TurbineServiceApplication {

    /**
     * 解决:
     *      hystrix dashboard Unable to connect to Command Metric Stream错误
     * springboot 版本如果是2.0则需要添加 ServletRegistrationBean 因为springboot的默认路径不是 "/hystrix.stream"，只要在自己的项目里配置上下面的servlet就可以了
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(TurbineServiceApplication.class, args);
    }
}
