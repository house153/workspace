package com.house.variety.modules.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.variety.modules.auth.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 描述  根据用户ID查询菜单信息<br/>
     * 参数  [userId] <br/>
     * 返回值  SysMenu> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/4 15:52
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    List<SysMenu> selectListByWrapper(@Param("ew") Wrapper<SysMenu> wrapper);

    /**
     * 描述  根据角色ID查询菜单标识<br/>
     * 参数  [roleId] <br/>
     * 返回值  String> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/9/4 15:51
     */
    List<String> selectMenusByRoleId(Long roleId);

    /**
     * 描述  根据用户ID查询所有菜单列表<br/>
     * 参数  [userId] <br/>
     * 返回值  List<SysMenu> <br/>
     * 创建人  HuangChao <br/>
     * 创建时间  2018/10/24 17:50
     */
    List<SysMenu> selectMenuAllByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long userId);


}
