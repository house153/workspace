package com.house.variety.service.impl;

import com.house.variety.dto.*;
import com.house.variety.mapper.DataCommonMapper;
import com.house.variety.service.DataCommonService;
import com.house.variety.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanfan on 2019-4-29.
 */
@Service
public class DataCommonServiceImpl implements DataCommonService {
    @Resource
    private DataCommonMapper dataCommonMapper;
    @Override
    public List<MonthlyTurnover> selectListMonthlyTurnover() {
        return dataCommonMapper.selectListMonthlyTurnover();
    }

    @Override
    public List<LatestOrderStatistics> selectLatestOrderStatistics() {
        return dataCommonMapper.selectLatestOrderStatistics();
    }

    @Override
    public List<MonthlyOrdersNumber> selectMonthlyOrdersNumber() {
        return dataCommonMapper.selectMonthlyOrdersNumber();
    }

    @Override
    public long selectCountWallet() {
        return dataCommonMapper.selectCountWallet();
    }

    @Override
    public long selectCountFreight() {
        return dataCommonMapper.selectCountFreight();
    }

    @Override
    public String selectNewOrder() {
        return dataCommonMapper.selectNewOrder();
    }

    @Override
    public long selectBizBranchCount() {
        return dataCommonMapper.selectBizBranchCount();
    }

    @Override
    public long selectSysUserCount() {
        return dataCommonMapper.selectSysUserCount();
    }

    @Override
    public List<MonthlyBizBranch> selectMonthlyBizBranch() {

        String [] a = DateUtils.getYearMonthByOld();
        List<MonthlyBizBranch> resList = new ArrayList<MonthlyBizBranch>();
        for(int i=0;i<a.length;i++){
            if(a[i]!=null){
                MonthlyBizBranch  monthlyBizBranch= new MonthlyBizBranch();
                monthlyBizBranch.setX(a[i]);
                monthlyBizBranch.setY(dataCommonMapper.selectBizBranchCountByMonth(a[i]));
                monthlyBizBranch.setZ(dataCommonMapper.selectSysUserCountByMonth(a[i]));
                resList.add(monthlyBizBranch);
            }
        }
        // 未过的年份 都为0
        String [] b = DateUtils.getYearMonthByNew();
        for(int j=0;j<b.length;j++){
            if(b[j]!=null){
                MonthlyBizBranch  monthlyBizBranch= new MonthlyBizBranch();
                monthlyBizBranch.setX(b[j]);
                monthlyBizBranch.setY(0);
                monthlyBizBranch.setZ(0);
                resList.add(monthlyBizBranch);
            }
        }
        return resList;
    }

    @Override
    public long selectCountCitys() {
        return dataCommonMapper.selectCountCitys();
    }

    @Override
    public long selectCountLine() {
        return dataCommonMapper.selectCountLine();
    }

    @Override
    public List<MonthlyLineAndCity> selectMonthlyLineAndCity() {

        String [] a = DateUtils.getYearMonthByOld();
        List<MonthlyLineAndCity> resList = new ArrayList<MonthlyLineAndCity>();
        for(int i=0;i<a.length;i++){
            if(a[i]!=null){
                MonthlyLineAndCity  monthlyBizBranch= new MonthlyLineAndCity();
                monthlyBizBranch.setX(a[i]);
                monthlyBizBranch.setY(dataCommonMapper.selectCountCitysByMonth(a[i]));
                monthlyBizBranch.setZ(dataCommonMapper.selectCountLineByMonth(a[i]));
                resList.add(monthlyBizBranch);
            }
        }
        // 未过的年份 都为0
        String [] b = DateUtils.getYearMonthByNew();
        for(int j=0;j<b.length;j++){
            if(b[j]!=null){
                MonthlyLineAndCity  monthlyBizBranch= new MonthlyLineAndCity();
                monthlyBizBranch.setX(b[j]);
                monthlyBizBranch.setY(0);
                monthlyBizBranch.setZ(0);
                resList.add(monthlyBizBranch);
            }
        }
        return resList;
    }
}
