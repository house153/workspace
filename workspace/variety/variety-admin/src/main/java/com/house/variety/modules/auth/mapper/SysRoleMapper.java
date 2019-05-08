package com.house.variety.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.variety.modules.auth.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRolesByUserId(Long userId);


    IPage<SysRole> pageList(Page<SysRole> page, @Param("ew") SysRole queryWrapper);
}
