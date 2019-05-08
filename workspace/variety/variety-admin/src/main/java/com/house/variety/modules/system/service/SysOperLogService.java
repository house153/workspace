package com.house.variety.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.system.entity.SysOperLog;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     *描述  分页条件查询<br/>
     *参数  [page, sysOperLog] <br/>
     *返回值  IPage<SysOperLog> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/24 11:32
    */
    IPage<SysOperLog> pageList(Page<SysOperLog> page, SysOperLog sysOperLog);
}
