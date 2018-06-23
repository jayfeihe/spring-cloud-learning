package com.jay.service;

import com.jay.bean.TestUser;
import com.jay.dao.TestUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TestUserService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private TestUserDao userDao;

    public TestUser createUser(TestUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public TestUser getUser(Long userId) {
        return userDao.getOne(userId);
    }

    @GetMapping("/findAll")
    public List<TestUser> findAll() {
        return userDao.findAll();
    }

}
