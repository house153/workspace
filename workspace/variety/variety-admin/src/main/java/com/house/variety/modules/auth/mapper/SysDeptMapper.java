package com.house.variety.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.variety.modules.auth.entity.SysDept;

/**
 * <p>
 * 系统部门表 Mapper 接口
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     *描述  根据ID查询部门信息<br/>
     *参数  [deptId] <br/>
     *返回值  SysDept <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/6 17:33
    */
    SysDept selectDeptById(Long deptId);

}
