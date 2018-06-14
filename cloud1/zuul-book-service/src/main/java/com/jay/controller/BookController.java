package com.jay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping("/{id}")
    public Book find(@PathVariable("id") int id) {
        return new Book(id, "Java " + id, "Jay He ", 66.66+id);
    }
}
