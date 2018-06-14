package com.jay.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 通过 RestTemplate调用服务接口    ---  Ribbon方式
 */
@Component
public class MyHystrixRibbonClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Spring Cloud Hystrix  配置属性参考：
     * https://blog.csdn.net/heartroll/article/details/78910834
     * https://blog.csdn.net/ruihin/article/details/77579794
     * https://blog.csdn.net/hry2015/article/details/78554846
     * 该服务的调用，将会使用自定义的线程池  jay-hystrix-thread-pool 执行
     */
    @HystrixCommand(fallbackMethod = "myFallback",
            threadPoolKey = "jay-hystrix-thread-pool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maximumSize", value = "10"), //设置线程池打最大值，默认为10，只有allowMaximumSizeToDivergeFromCoreSize为true时才生效
                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1")
            },
            commandKey = "jay-hystrix-command",
            commandProperties = {
                    //设置启动熔断的错误或者延迟的比例，默认是50%。在一个滚动窗口（默认10s）如果超过50%的请求发生错误或者延迟，则触发熔断器,前提是circuitBreaker.requestVolumeThreshold条件满足
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10")
            }
    )
    public String simpleHystrixClientCall(int param) {
        try {
            //这里如果休眠1.5秒，会导致超时   ---  Hystrix调用默认超时时间是1秒
            Thread.sleep(100);
            System.out.println("Hystrix:Thread:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return restTemplate.getForEntity("http://compute-service/compute/person/" + param, String.class).getBody();
    }

    /**
     * 方法simpleHystrixClientCall的回退方法，可以指定将hystrix执行失败异常传入到方法中
     *
     * @param p hystrix执行失败的传入方法的请求
     * @param e hystrix执行失败的异常对象
     * @return
     */
    String myFallback(int p, Throwable e) {
        return "Execute raw fallback: access service fail , req= " + p + " reason = " + e;
    }
}
