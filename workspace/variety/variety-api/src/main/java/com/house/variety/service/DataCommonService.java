package com.house.variety.service;

import com.house.variety.dto.*;

import java.util.List;

/**
 * Created by tanfan on 2019-4-29.
 */
public interface DataCommonService {
    // 1.	月交易额
    List<MonthlyTurnover> selectListMonthlyTurnover();
    // 今日/昨日/累积 订单票数、件数、重量、体积
    List<LatestOrderStatistics> selectLatestOrderStatistics();
    // 月订单及货物件数
    List<MonthlyOrdersNumber> selectMonthlyOrdersNumber();
    // 累计钱包交易额
    long selectCountWallet();
    // 累计运费交易额
    long selectCountFreight();
    // 新增订单
    String selectNewOrder();
    // 物流企业累计用户数：
    long selectBizBranchCount();
    //收发货企业累计用户数
    long selectSysUserCount();

    //物流企业累计用户数 收发货企业累计用户数 每月汇总
    List<MonthlyBizBranch> selectMonthlyBizBranch();

   //累计覆盖城市
    long selectCountCitys();
    //累计开通线路
    long selectCountLine();

    //累计覆盖城市 累计开通线路  每月汇总
    List<MonthlyLineAndCity> selectMonthlyLineAndCity();
}
