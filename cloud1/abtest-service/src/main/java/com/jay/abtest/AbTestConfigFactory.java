package com.jay.abtest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理所有AbTest
 * 获取、匹配AbTest值
 * 决定AbTest走向
 */
@Data
@Component
public class AbTestConfigFactory {
    @Autowired
    private InsuranceAbTestConfig insuranceAbTestConfig;
}
