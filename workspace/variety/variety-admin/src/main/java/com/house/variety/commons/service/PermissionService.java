package com.house.variety.commons.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 描述  js调用 thymeleaf 实现按钮权限可见性<br/>
 * 参数   <br/>
 * 返回值   <br/>
 * 创建人  HuangChao <br/>
 * 创建时间  2018/9/19 17:00
 */
@Service("permission")
public class PermissionService {
    public String hasPermi(String permission) {
        return isPermittedOperator(permission) ? "" : "hidden";
    }


    private boolean isPermittedOperator(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

}
