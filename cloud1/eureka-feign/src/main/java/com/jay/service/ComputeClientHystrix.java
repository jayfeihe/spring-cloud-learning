package com.jay.service;

import org.springframework.stereotype.Component;

@Component
public class ComputeClientHystrix implements ComputeClient{
    @Override
    public Integer add(Integer a, Integer b) {
        return -9999;
    }

    @Override
    public String findPerson(Integer id) {
        return "find person error";
    }
}
