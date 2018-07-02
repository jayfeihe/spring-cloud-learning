package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 特别注意：
 *  1.这里的AbTest中，暂不支持通过app区分
 *  2.正常配置项和比例，取出后，需要放入redis
 *      --缓存实现  getConfigByAppAndKey
 *
 *
 *
 *
 *  3.提供了一个缓存统一操作的抽象封装    JayCache
 *    可自定义缓存失效，继承 JayChche，实现缓存存取和失效逻辑
 *          ---   一般用于，设置一批缓存内容时使用, 可在CommandLine接口中设置
 *
 *
 *
 *  4.提供一个分布式的定时任务处理
 *      Consumer            ---     接口，实现类处理具体定时任务
 *      XxConsumer          ---     定时任务真正执行服务类，实现Consumer接口，需要设置一个唯一的BeanName，用于通过Ioc，getBean(beanName)获取执行服务对象
 *
 *
 *      ConsumerExecutor    ---     真正执行定时任务的线程， 通过getBean(beanName)  调用指定Bean(Consumer实现类)执行，传入执行参数
 *      ConsumerContainer   ---     定时取任务的逻辑，将定时任务从DB取出(根据时间区间或数量)，放入阻塞队列，如果是多个JVM执行，通过zk指定只让master或指定slave执行
 *
 *      DBMessage           ---     存放定时任务信息，包括：定时任务的bean标识，执行参数，任务执行状态，创建和执行完成时间，msgKey(一条定时任务标识)
 *
 *      DBQueue             ---     创建定时任务记录到DB     ---  接口
 *      DBQueueImpl         ---     创建定时任务记录到DB     ---  实现类
 *
 */
@SpringBootApplication
public class AbtestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbtestServiceApplication.class, args);
    }
}
