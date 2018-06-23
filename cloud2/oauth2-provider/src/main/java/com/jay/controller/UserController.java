package com.jay.controller;


import com.jay.domain.User;
import com.jay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 采用RemoteTokenServices方式对Token进行验证，
     * 如果其他资源服务需要验证Token，则需要远程调用授权服务器暴露的验证Token的API接口，
     *
     * 此接口为：验证token的API接口
     *
     *
     * @param principal
     * @return
     */
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @GetMapping("/useId")
    public User getUser(Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findByName")
    public User findByName(String username) {
        return userService.findByName(username);
    }


}
