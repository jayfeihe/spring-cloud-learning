package com.jay.controller;

import com.jay.bean.TestUser;
import com.jay.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private TestUserService userService;

    @GetMapping("/registry")
    public TestUser createUser(String username, String password){
        TestUser user = new TestUser();
        user.setUsername(username);
        user.setPassword(password);
        return userService.createUser(user);
    }

    @GetMapping("/useId")
    public TestUser getUser(Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/findAll")
    public List<TestUser> findAll() {
        return userService.findAll();
    }
}
