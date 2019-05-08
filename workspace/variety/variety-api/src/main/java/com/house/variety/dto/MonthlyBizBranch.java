package com.house.variety.dto;

import lombok.Data;

/**
 * @Auther: tanfan
 * @Date: 2019/5/6 11:06
 * @Description:  物流企业/用户 每月统计
 */
@Data
public class MonthlyBizBranch {
    private String x;// 日期 2010/01/01 00:00:00

    private long y;//  物流企业数量

    private long z;//  收发货用户数量
}
