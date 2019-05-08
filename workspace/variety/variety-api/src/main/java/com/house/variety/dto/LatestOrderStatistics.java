package com.house.variety.dto;

import lombok.Data;

/**
 * @Auther: tanfan
 * @Date: 2019/5/6 10:08
 * @Description: 今日/昨日/累积 订单票数、件数、重量、体积
 */
@Data
public class LatestOrderStatistics {

    private String title;

    private String pv;
}
