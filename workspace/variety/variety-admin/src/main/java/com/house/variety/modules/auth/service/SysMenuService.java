package com.house.variety.modules.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.house.variety.modules.auth.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     *
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 描述  获取角色菜单标识列表<br/>
     * 参数  [roleId] <br/>
     * 返回值  String> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/4 15:59
     */
    List<String> selectMenusByRoleId(Long roleId);

    /**
     * 描述  获取角色列表菜单标识列表<br/>
     * 参数  [userId] <br/>
     * 返回值  String> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/4 15:59
     */
    List<SysMenu> selectMenusByRoleIds(Long userId);

    /**
     * 获取菜单列表 主页查询用户菜单用
     *
     * @return
     */
    List<SysMenu> selectMenus();

    /**
     *描述  获取所有资源信息列表<br/>
     *参数  [] <br/>
     *返回值  sysMenu> <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/17 10:38
    */
    List<SysMenu> selectAll();

    /**
     * 获取菜单列表信息
     *
     * @return
     */
    List<SysMenu> list(SysMenu menu);


    /**
     * 获取菜单列表,根据父级菜单ID
     *
     * @return
     */
    List<SysMenu> selectMenusByParentId(Long parentId);

    /**
     * 描述  根据ID查询菜单信息<br/>
     * 参数  [menuId] <br/>
     * 返回值  SysMenu <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/8/30 14:29
     */
    SysMenu selectMenuById(Long menuId);

    /**
     * 校验权限标识是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean checkPermsUnique(SysMenu menu);


    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(SysMenu menu);


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     *描述  菜单更新<br/>
     *参数  [menu] <br/>
     *返回值  boolean <br/>
     *创建人  HuangChao <br/>
     *创建时间  2018/10/17 18:05
    */
    boolean update(SysMenu menu);

}
