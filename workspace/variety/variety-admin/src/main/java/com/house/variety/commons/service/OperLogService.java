package com.house.variety.commons.service;

import com.house.variety.modules.system.entity.SysOperLog;
import com.house.variety.modules.system.service.SysOperLogService;
import com.house.variety.seq.IdUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/24 15:10
 * @描述 操作日志服务类
 */
@Service("operLogService")
public class OperLogService {

    @Resource
    private SysOperLogService sysOperLogService;

    /**
     *描述  保存日志<br/>
     *参数  [operLog] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/24 15:12
    */
    public boolean saveLog(SysOperLog operLog){
        operLog.setOperId(IdUtil.getId());
        return sysOperLogService.save(operLog);
    }
}
