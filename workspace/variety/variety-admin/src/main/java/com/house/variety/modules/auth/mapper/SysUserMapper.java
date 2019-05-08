package com.house.variety.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.variety.modules.auth.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserById(Long userId);

//    IPage<SysUser> pageList(Page<SysUser> page, @Param("ew") Wrapper<SysUser> queryWrapper);

    IPage<SysUser> pageList(Page<SysUser> page, @Param("ew") SysUser queryWrapper);

}
