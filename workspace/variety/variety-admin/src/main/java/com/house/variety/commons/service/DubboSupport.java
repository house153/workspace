package com.house.variety.commons.service;

import com.house.variety.modules.auth.service.SysDeptService;
import com.house.variety.modules.auth.service.SysMenuService;
import com.house.variety.modules.auth.service.SysRoleService;
import com.house.variety.modules.auth.service.SysUserService;
import com.house.variety.modules.system.service.SysLogininforService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/16 17:37
 * @描述
 */
@Component("dubboSupport")
public class DubboSupport {

    @Resource
    public SysMenuService menuService;

    @Resource
    public SysRoleService roleService;

    @Resource
    public SysUserService userService;

    @Resource
    public SysDeptService deptService;

    @Resource
    public SysLogininforService logininforService;
}
