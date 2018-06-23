package com.jay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class HiController {

    @GetMapping("/hi")
    public String home(){
        return "Hi, I'm from Chian";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //
    @RequestMapping("/hello")
    public String hello (){
        return "hello you!";
    }
    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal,
                                             Authentication authentication){

        log.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        log.info(oAuth2Authentication.toString());
        log.info("principal.toString()"+principal.toString());
        log.info("principal.getName()"+principal.getName());
        log.info("authentication:"+authentication.getAuthorities().toString());

        return oAuth2Authentication;

    }
}
