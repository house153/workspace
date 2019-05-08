package com.house.variety.dto;

import lombok.Data;

/**
 * @Auther: tanfan
 * @Date: 2019/5/6 10:29
 * @Description: 月订单及货物件数
 */
@Data
public class MonthlyOrdersNumber {
    private String x;// 日期 2010/01/01 00:00:00

    private String y;//  订单数

    private String z;//  货物件数
}
