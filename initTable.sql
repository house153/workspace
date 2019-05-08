-- 彩种表
CREATE TABLE `t_color_variety` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(255) NOT NULL COMMENT '彩种名称',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `introduce` VARCHAR(255) NOT NULL COMMENT '彩种简介',
  `rule_url`  VARCHAR(255) NOT NULL COMMENT '彩种规则url',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='彩种表';
-- 香港六合彩对应期号表
CREATE TABLE `t_kong_variety` (
  `id` BIGINT(20) NOT NULL,
  `year` VARCHAR(10) NOT NULL COMMENT '年',
  `number` VARCHAR(20) NOT NULL COMMENT '期号',
  `staus`  TINYINT NOT NULL COMMENT '开奖状态 1 已开 2未开',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='香港六合期号表';

-- 极速六合彩对应期号表
CREATE TABLE `t_speed_variety` (
  `id` BIGINT(20) NOT NULL,
  `year` VARCHAR(10) NOT NULL COMMENT '年',
  `month` VARCHAR(10) NOT NULL COMMENT '月',
  `day` VARCHAR(10) NOT NULL COMMENT '日',
  `number` VARCHAR(20) NOT NULL COMMENT '期号',
  `staus`  TINYINT NOT NULL COMMENT '开奖状态 1 已开 2未开',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='极速六合期号表';

-- 香港六合彩开奖结果表
CREATE TABLE `t_kong_lottery` (
  `id` BIGINT(20) NOT NULL,
  `k_id` BIGINT(20) NOT NULL COMMENT '期号id',
   
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='香港六合彩开奖结果表';

-- 极速六合彩开奖结果表

-- 用户表 
DROP TABLE  `t_user`;
CREATE TABLE `t_user` (
  `id` BIGINT(20) NOT NULL,
  `number` VARCHAR(10) NOT NULL COMMENT '唯一标识账号id',
  `name` VARCHAR(10) DEFAULT NULL COMMENT '昵称',
  `password` VARCHAR(255) DEFAULT NULL COMMENT '登录密码',
  `o_id` BIGINT(20) DEFAULT NULL COMMENT '代理商id',
  `status` TINYINT DEFAULT NULL COMMENT '帐号状态（0正常 1停用 2申请代理商中）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 代理商表
CREATE TABLE `t_operators` (
  `id` BIGINT(20) NOT NULL,
  `phone` VARCHAR(15) NOT NULL COMMENT '唯一标识手机号码',
  `password` VARCHAR(255) DEFAULT NULL COMMENT '登录密码',
  `status` TINYINT DEFAULT NULL COMMENT '帐号状态（0正常 1停用 2申请中）',
  `quota`  BIGINT(20) NOT NULL COMMENT '额度 暂时只用极速最大赔付额度',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='代理商表';
 
-- 系统参数表 （代理商 盈利比例 平台分成比例）
CREATE TABLE `sys_config` (
  `config_id` BIGINT(20) NOT NULL COMMENT '参数主键',
  `config_name` VARCHAR(100) DEFAULT '' COMMENT '参数名称',
  `config_key` VARCHAR(100) DEFAULT '' COMMENT '参数键名',
  `config_value` VARCHAR(100) DEFAULT '' COMMENT '参数键值',
  `config_type` CHAR(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` VARCHAR(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;


-- 香港六合彩投注详情表

-- 极速六合彩投注详情表

-- 代理商钱包表 （砖石 1=1）
CREATE TABLE `t_operators_wallet` (
 `o_id` BIGINT(20) NOT NULL COMMENT '代理商id',
 `money` BIGINT(20) NOT NULL COMMENT '钱包余额',
 `bond` BIGINT(20) NOT NULL COMMENT '保证金',
 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`o_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;

-- 用户钱包表 （金币，砖石）
CREATE TABLE `t_user_wallet` (
 `u_id` BIGINT(20) NOT NULL COMMENT '用户id',
 `money` BIGINT(20) NOT NULL COMMENT '钱包余额',
 `coin` BIGINT(20) NOT NULL COMMENT '金币',
 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`u_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;


-- 代理商钱包流水表 （1.平台充值 2.用户充值 3.平台分成 4.盈利返现 ）

-- 用户钱包流水表 （1.充值 2.投注 3.中奖 4.提现）

-- 香港六合彩每期代理商投注情况汇总表 

-- 极速六合彩每期代理商投注情况汇总表

-- 香港六合彩投注方式及赔率 

-- 极速六合彩投注方式及赔率 

