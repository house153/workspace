package com.house.variety.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.variety.modules.auth.entity.SysUserRole;
import com.house.variety.modules.auth.mapper.SysUserRoleMapper;
import com.house.variety.modules.auth.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    public Integer selectCountByRole(List<String> roles){
        return this.count(new QueryWrapper<SysUserRole>().lambda().in(SysUserRole::getRoleId,roles));
    }

}
