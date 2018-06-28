package com.jay.abtest.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigQuery {
    private int id;
    private String cfgApp; // 应用
    private String cfgKey; // 配置键
    private String cfgValue; // 配置值
    private String cfgDesc; // 配置说明
    private int pageNum;
    private int pageSize;
}
