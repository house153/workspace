package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysUser;

import java.util.Set;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysUserService extends IService<SysUser> {

    /**
     *描述  分页查询<br/>
     *参数  [page, user] <br/>
     *返回值  IPage<SysUser> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/12 18:05
    */
    IPage<SysUser> pageList(Page<SysUser> page, SysUser user);

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean insertUser(SysUser user);

    /**
     *描述  更新用户信息<br/>
     *参数  [user] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/9/12 14:39
    */
    boolean updateUser(SysUser user);
    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    boolean checkLoginNameUnique(String loginName);

    /**
     * 校验用户手机号码是否唯一
     *
     * @param user 用户
     * @return
     */
    boolean checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户
     * @return
     */
    boolean checkEmailUnique(SysUser user);

    /**
     *描述  用户登录，用户名、手机号码、邮箱<br/>
     *参数  [user] <br/>
     *返回值  SysUser <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/16 14:24
    */
    SysUser selectUserByLoginName(SysUser user);

    /**
     *描述  根据用户ID查询角色ID列表<br/>
     *参数  [userId] <br/>
     *返回值 Set<Long> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/25 16:04
    */
    Set<Long> selectRoleIdByUserId(Long userId);


    /**
     *描述 根据ID查询用户信息<br/>
     *参数  [userId] <br/>
     *返回值  SysUser <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/26 9:05
    */
    SysUser selectUserById(Long userId);

    /**
     *描述  根据部门查询用户数<br/>
     *参数  [deptId] <br/>
     *返回值  java.lang.Integer <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/12/24 9:48
    */
    Integer selectCountByDeptId(Long deptId);
}
