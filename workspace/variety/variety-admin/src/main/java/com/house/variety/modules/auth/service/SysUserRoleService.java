package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     *描述  根据角色ID查询是否存在用户<br/>
     *参数  [roles] <br/>
     *返回值  java.lang.Integer <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/25 14:49
    */
    Integer selectCountByRole(List<String> roles);
}
