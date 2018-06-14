package com.jay.controller;

import com.jay.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/compute")
@Slf4j
public class ComputeController {

    @Autowired
    private DiscoveryClient client;

    /**
     * 查看服务注册中心中注册的服务实例列表
     */
    @RequestMapping("services")
    public List<String> clientList() {
        List<String> serviceIds = client.getServices();
        for (String serviceId : serviceIds) {
            List<ServiceInstance> instances = client.getInstances(serviceId);
            for (ServiceInstance instance : instances) {
                System.out.println("ServiceId:" + instance.getServiceId() + ",Host:" + instance.getHost() + ",Port:" + instance.getPort() + ",URI:" + instance.getUri() + ",MetaData:" + instance.getMetadata());
            }
        }
        return serviceIds;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        List<ServiceInstance> instances = client.getInstances("compute-service");
        ServiceInstance instance = instances.get(0);
        Integer r = a + b;
        log.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    @GetMapping("/person/{id}")
    public Person findPerson(@PathVariable("id") int id) {
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Person person = new Person(id, "name" + id, 10 + id, "message" + id);
        return person;
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        Thread.sleep(800);
        return "Hello World";
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findPersons(@RequestBody List<Integer> personIds, HttpServletRequest request) {
        List<Person> result = new ArrayList<Person>();
        for(Integer id : personIds) {
            Person person = new Person();
            person.setId(id);
            person.setName("Jay");
            person.setAge(new Random().nextInt(30));
            person.setMessage(request.getRequestURL().toString());
            result.add(person);
        }
        return result;
    }
}
