package com.jay.service;


import com.jay.domain.User;

import java.util.List;

public interface UserService {

	void create(User user);

	User getUser(Long userId);

	List<User> findAll();

	User findByName(String username);

}
