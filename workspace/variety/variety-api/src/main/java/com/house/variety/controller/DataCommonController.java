package com.house.variety.controller;

import com.house.variety.dto.MonthlyBizBranch;
import com.house.variety.dto.MonthlyLineAndCity;
import com.house.variety.dto.MonthlyOrdersNumber;
import com.house.variety.dto.MonthlyTurnover;
import com.house.variety.service.DataCommonService;
import com.house.variety.util.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tanfan
 * @Date: 2019/5/6 09:47
 * @Description:
 */
@RestController
public class DataCommonController {
    @Resource
    private DataCommonService dataCommonService;
    /**
     * 查询月交易额
     * @param
     * @param
     * @return
     */
    @RequestMapping("/monthlyTurnover")
    public Object monthlyTurnover(){
        List<MonthlyTurnover> list = dataCommonService.selectListMonthlyTurnover();
        for(MonthlyTurnover monthlyTurnover:list){
            monthlyTurnover.setX(DateUtils.getDateTransf(monthlyTurnover.getX()));
        }
        return list;
    }
    /**
     * 查询 今日/昨日/累计 订单票数、件数、重量、体积
     * @param
     * @param
     * @return
     */
    @RequestMapping("/latestOrderStatistics")
    public Object LatestOrderStatistics(){
        return dataCommonService.selectLatestOrderStatistics();
    }

    /**
     * 查询 月订单及货物件数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/monthlyOrdersNumber")
    public Object monthlyOrdersNumber(){
        List<MonthlyOrdersNumber> list = dataCommonService.selectMonthlyOrdersNumber();
        for(MonthlyOrdersNumber monthlyOrdersNumber:list){
            monthlyOrdersNumber.setX(DateUtils.getDateTransf(monthlyOrdersNumber.getX()));
        }
        return list;
    }

    /**
     * 查询 累计钱包交易额
     * @param
     * @param
     * @return
     */
    @RequestMapping("/countWallet")
    public Object countWallet(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value",dataCommonService.selectCountWallet());
        list.add(resMap);
        return list;
    }

    /**
     * 查询 累计运费交易额
     * @param
     * @param
     * @return
     */
    @RequestMapping("/countFreight")
    public Object countFreight(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value",dataCommonService.selectCountFreight());
        list.add(resMap);
        return list;
    }

    /**
     * 查询 新增订单
     * @param
     * @param
     * @return
     */
    @RequestMapping("/newOrder")
    public Object newOrder(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value",dataCommonService.selectNewOrder());
        list.add(resMap);
        return list;
    }

    /**
     * 查询 物流企业累计用户数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/bizBranchCount")
    public Object bizBranchCount(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value","物流企业累计用户数: "+dataCommonService.selectBizBranchCount());
        resMap.put("url","");
        list.add(resMap);
        return list;
    }

    /**
     * 查询 收发货企业累计用户数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/sysUserCount")
    public Object sysUserCount(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value","收发货企业累计用户数: "+dataCommonService.selectSysUserCount());
        resMap.put("url","");
        list.add(resMap);
        return list;
    }

    /**
     * 查询 每月 物流企业累计用户数 和 收发货企业累计用户数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/monthlyBizBranch")
    public Object monthlyBizBranch(){
        List<MonthlyBizBranch> list = dataCommonService.selectMonthlyBizBranch();
        for(MonthlyBizBranch monthlyBizBranch:list){
            monthlyBizBranch.setX(DateUtils.getDateTransf(monthlyBizBranch.getX()));
        }
        return list;
    }

    /**
     * 查询 累计覆盖城市
     * @param
     * @param
     * @return
     */
    @RequestMapping("/countCitys")
    public Object countCitys(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value","累计覆盖城市: "+dataCommonService.selectCountCitys()+"座");
        resMap.put("url","");
        list.add(resMap);
        return list;
    }
    /**
     * 查询 累计开通线路数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/countLine")
    public Object countLine(){
        ArrayList list = new ArrayList();
        Map resMap = new HashMap();
        resMap.put("value","累计开通线路: "+dataCommonService.selectCountLine()+"条");
        resMap.put("url","");
        list.add(resMap);
        return list;
    }

    /**
     * 查询 每月 累计覆盖城市 和 累计开通线路数
     * @param
     * @param
     * @return
     */
    @RequestMapping("/monthlyLineAndCity")
    public Object MonthlyLineAndCity(){
        List<MonthlyLineAndCity> list = dataCommonService.selectMonthlyLineAndCity();
        for(MonthlyLineAndCity monthlyLineAndCity:list){
            monthlyLineAndCity.setX(DateUtils.getDateTransf(monthlyLineAndCity.getX()));
        }
        return list;
    }
}
