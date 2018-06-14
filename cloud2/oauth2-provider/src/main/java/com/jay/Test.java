package com.jay;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String args[]){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String str = bCryptPasswordEncoder.encode("123456");
        System.out.println(str);
    }
}
