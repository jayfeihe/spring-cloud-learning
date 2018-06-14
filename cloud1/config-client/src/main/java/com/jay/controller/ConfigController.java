package com.jay.controller;

import com.jay.properties.ConfigUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Autowired
    private ConfigUser configUser;

    @GetMapping("/test1")
    public ConfigUser test1() {
        return configUser;
    }


    @Value("${test.name}")
    private String name;

    @GetMapping(value = "/test2", produces="text/plain;charset=UTF-8")
    public String test2() {
        return name;
    }


    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/foo")
    public String hi() {
        return foo;
    }
}
