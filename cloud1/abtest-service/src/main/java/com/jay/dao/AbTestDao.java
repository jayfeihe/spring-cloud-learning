package com.jay.dao;

import com.jay.bean.AbTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbTestDao extends JpaRepository<AbTest, Long> {

    public AbTest getByTestNameAndTestKey(String name, String key);
}
