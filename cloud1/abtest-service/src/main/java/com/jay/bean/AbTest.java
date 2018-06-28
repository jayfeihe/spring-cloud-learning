package com.jay.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/*
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ab_test`
-- ----------------------------
DROP TABLE IF EXISTS `ab_test`;
CREATE TABLE `ab_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `test_name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `test_key` varchar(32) NOT NULL DEFAULT '' COMMENT 'key',
  `test_value` varchar(32) NOT NULL DEFAULT '' COMMENT 'value',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ab_nk` (`test_name`,`test_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='abtest';

SET FOREIGN_KEY_CHECKS = 1;
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "ab_test")
public class AbTest {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_key")
    private String testKey;

    @Column(name = "test_value")
    private String testValue;

    @Column(name = "create_time")
    private Date createTime;
}
