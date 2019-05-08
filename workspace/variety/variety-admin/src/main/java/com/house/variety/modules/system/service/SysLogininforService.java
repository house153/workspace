package com.house.variety.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.system.entity.SysLogininfor;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysLogininforService extends IService<SysLogininfor> {

    IPage<SysLogininfor> pageList(Page<SysLogininfor> page, SysLogininfor logininfor);
}
