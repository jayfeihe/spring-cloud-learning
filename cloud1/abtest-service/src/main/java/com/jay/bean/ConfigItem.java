package com.jay.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户配置AbTest
 */
/*
create table config_item (
            id int AUTO_INCREMENT comment '编号',
            cfg_app varchar(64) comment '应用',
            cfg_key varchar(64) not null default '' comment '配置键',
            cfg_value varchar(256) not null default '' comment '配置值',
            cfg_desc varchar(256) not null default '' comment '配置说明',
            primary key (id),
            unique index (cfg_app, cfg_key)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '配置';
 */
@NoArgsConstructor
@Entity
@Table(name = "config_item")
public class ConfigItem implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "cfg_app", nullable = true)
    private String cfgApp; // 应用
    @Column(name = "cfg_key")
    private String cfgKey; // 配置键
    @Column(name = "cfg_value")
    private String cfgValue; // 配置值
    @Column(name = "cfg_desc")
    private String cfgDesc; // 配置说明

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCfgApp() {
        return cfgApp;
    }

    public void setCfgApp(String cfgApp) {
        this.cfgApp = cfgApp;
    }

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    public String getCfgDesc() {
        return cfgDesc;
    }

    public void setCfgDesc(String cfgDesc) {
        this.cfgDesc = cfgDesc;
    }
}
