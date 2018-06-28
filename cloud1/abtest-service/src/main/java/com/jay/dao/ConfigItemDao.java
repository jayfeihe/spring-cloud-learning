package com.jay.dao;

import com.jay.bean.ConfigItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigItemDao extends JpaRepository<ConfigItem, Integer> {

    public ConfigItem getByCfgAppAndCfgKey(String app, String key);

    public ConfigItem getByCfgKey(String key);

}
