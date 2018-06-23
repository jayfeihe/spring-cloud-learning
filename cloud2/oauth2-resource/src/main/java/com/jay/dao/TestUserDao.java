package com.jay.dao;

import com.jay.bean.TestUser;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserDao extends JpaRepository<TestUser, Long> {
}
