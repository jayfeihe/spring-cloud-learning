package com.jay.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComputeService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService(int a, int b) {
//        Map<String, Object> param = new HashMap<>();
//        param.put("a", a);
//        param.put("b", b);
        /**
         * 这里的value是，提供者提供的，访问接口的全路径
         */
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/compute/add?a=" + a + "&b=" + b, String.class).getBody();
    }

    public String addServiceFallback(int a, int b) {
        return "error";
    }

    public String findPerson(int id) {
        return restTemplate.getForObject("http://COMPUTE-SERVICE/compute/person/" + id, String.class);
    }
}
