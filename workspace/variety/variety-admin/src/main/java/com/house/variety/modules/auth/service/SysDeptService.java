package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysDept;

import java.util.List;

/**
 * <p>
 * 系统部门表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
public interface SysDeptService extends IService<SysDept> {


    /**
     * 描述  列表查询<br/>
     * 参数  [sysDept] <br/>
     * 返回值  List<SysDept> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 16:53
     */
    List<SysDept> listByDept(SysDept sysDept);

    /**
     *描述  根据ID查询部门信息<br/>
     *参数  [deptId] <br/>
     *返回值  SysDept <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/6 17:35
    */
     SysDept selectDeptById(Long deptId);

    /**
     * 描述  校验部门名称<br/>
     * 参数  [dept] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 16:49
     */
    boolean checkDeptNameUnique(SysDept dept);


    /**
     * 描述  判断是否存在下级部门<br/>
     * 参数  [parentId] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 16:59
     */
    boolean selectDeptCount(Long parentId);

    /**
     * 描述  部门是存在用户<br/>
     * 参数  [deptId] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 17:00
     */
    boolean checkDeptExistUser(Long deptId);


    /**
     *描述  新增部门信息<br/>
     *参数  [dept] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/17 22:38
    */
    boolean saveDept(SysDept dept);

    /**
     *描述  修改保存部门信息<br/>
     *参数  [dept] <br/>
     *返回值  int <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/17 22:38
    */
    boolean updateDept(SysDept dept);
}
