package com.jay.controller;

import com.jay.service.MyHystrixRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix/ribbon")
public class HystrixRibbonController {

    @Autowired
    private MyHystrixRibbonClient hystrixRibbonClient;

    @GetMapping("/person/{id}")
    public String findPerson(@PathVariable("id") int id) {
        return hystrixRibbonClient.simpleHystrixClientCall(id);
    }
}
