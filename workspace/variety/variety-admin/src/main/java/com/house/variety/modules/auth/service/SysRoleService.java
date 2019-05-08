package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysDept;
import com.house.variety.modules.auth.entity.SysRole;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysRoleService extends IService<SysRole> {


    /**
     * 描述  分页查询<br/>
     * 参数  [role] <br/>
     * 返回值  SysRole> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/6 9:23
     */
    IPage<SysRole> pageList(Page<SysRole> page, SysRole role);

    /**
     * 描述  保存角色信息<br/>
     * 参数  [role] <br/>
     * 返回值  boolean <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/4 16:55
     */
    boolean saveRole(SysRole role);

    /**
     *描述  更新角色信息<br/>
     *参数  [role] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/17 9:37
    */
    boolean updateRole(SysRole role);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleKeyUnique(SysRole role);

    /**
     * 描述  根据用户ID查询角色列表<br/>
     * 参数  [userId] <br/>
     * 返回值  List<SysRole> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/12 11:30
     */
    List<SysRole> selectRoleByUserId(Long userId);


    /**
     *描述  根据部门查询对应角色列表<br/>
     *参数  [dept] <br/>
     *返回值  List<SysRole> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/19 15:31
    */
    List<SysRole> selectRoleByDeptId(SysDept dept);

    /**
     *描述 根据部门、用户ID查询对应角色列表 <br/>
     *参数  [deptId, userId] <br/>
     *返回值  List<SysRole> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/20 9:36
    */
    List<SysRole> selectRoleByUserId(Long deptId, Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRoleKeys(Long userId);


    /**
     *描述  根据部门ID查询结果集<br/>
     *参数  [deptId] <br/>
     *返回值  Integer <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/24 13:50
    */
    Integer selectCountByDeptId(Long deptId);
}
