package com.jay.controller;

import com.jay.service.ComputeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer/feign")
public class FeignConsumerController {

    @Autowired
    private ComputeClient computeClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(int a, int b) {
        return computeClient.add(a, b);
    }

    @GetMapping("/person/{id}")
    public String findPerson(@PathVariable("id") Integer id) {
        return computeClient.findPerson(id);
    }
}
