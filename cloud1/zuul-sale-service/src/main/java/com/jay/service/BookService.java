package com.jay.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "zuul-book-provider")
public interface BookService {

    @GetMapping("/book/{id}")
    public Book find(@PathVariable("id") int id);
}
