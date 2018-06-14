package com.jay.controller;

import com.jay.service.ComputeService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer/ribbon")
public class RibbonConsumerController {

    @Autowired
    private ComputeService computeService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(int a, int b) {
        return computeService.addService(a, b);
    }

    @GetMapping("/person/{id}")
    public String findPerson(@PathVariable("id") int id) {
        return computeService.findPerson(id);
    }


    /**
     * 查询在线的服务信息列表
     */
    @GetMapping("/router")
    public List route() {
        List<Object> list = new ArrayList<>();
        //查找服务列表
        List<ServiceInstance> ins = getServiceInstances();
        for (ServiceInstance service : ins) {
            EurekaDiscoveryClient.EurekaServiceInstance esi = (EurekaDiscoveryClient.EurekaServiceInstance) service;
            InstanceInfo info = esi.getInstanceInfo();
            System.out.println(info.getAppName() + "---" + info.getInstanceId()
                    + "---" + info.getStatus());
            list.add(info);
        }
        return list;
    }

    /**
     * 查询可用服务
     */
    private List<ServiceInstance> getServiceInstances() {
        List<String> ids = discoveryClient.getServices();
        List<ServiceInstance> result = new ArrayList<ServiceInstance>();
        for (String id : ids) {
            List<ServiceInstance> ins = discoveryClient.getInstances(id);
            result.addAll(ins);
        }
        return result;
    }


    @Autowired
    private LoadBalancerClient loadBalancer;

    /**
     * 查找指定服务名(serviceId)的信息
     */
    @RequestMapping(value = "/uselb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceInstance uselb() {
        // 查找服务器实例
//        ServiceInstance si = loadBalancer.choose("cloud-provider");

        ServiceInstance si = loadBalancer.choose("COMPUTE-SERVICE");

        return si;
    }

    @Autowired
    private SpringClientFactory factory;

    @RequestMapping(value = "/defaultValue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String defaultValue() {
        System.out.println("==== 输出默认配置：");
        // 获取默认的配置
        ZoneAwareLoadBalancer alb = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("default");
        System.out.println("    IClientConfig: "
                + factory.getLoadBalancer("default").getClass().getName());
        System.out.println("    IRule: " + alb.getRule().getClass().getName());
        System.out.println("    IPing: " + alb.getPing().getClass().getName());
        System.out.println("    ServerList: "
                + alb.getServerListImpl().getClass().getName());
        System.out.println("    ServerListFilter: "
                + alb.getFilter().getClass().getName());
        System.out.println("    ILoadBalancer: " + alb.getClass().getName());
        System.out.println("    PingInterval: " + alb.getPingInterval());
        System.out.println("==== 输出 cloud-provider 配置：");
        // 获取 cloud-provider 的配置
        ZoneAwareLoadBalancer alb2 = (ZoneAwareLoadBalancer) factory
                .getLoadBalancer("cloud-provider");
        System.out.println("    IClientConfig: "
                + factory.getLoadBalancer("cloud-provider").getClass()
                .getName());
        System.out.println("    IRule: " + alb2.getRule().getClass().getName());
        System.out.println("    IPing: " + alb2.getPing().getClass().getName());
        System.out.println("    ServerList: "
                + alb2.getServerListImpl().getClass().getName());
        System.out.println("    ServerListFilter: "
                + alb2.getFilter().getClass().getName());
        System.out.println("    ILoadBalancer: " + alb2.getClass().getName());
        System.out.println("    PingInterval: " + alb2.getPingInterval());
        return "";
    }
}
