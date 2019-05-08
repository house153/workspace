package com.house.variety.modules.auth.entity;

import com.house.variety.modules.BaseEntity;
import lombok.Data;


/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 权限类型,1:操作权限,2:分配权限
     */
    private Integer authorityType;


    @Override
    public String toString() {
        return "SysRoleMenu{" +
        ", roleId=" + roleId +
        ", menuId=" + menuId +
        ", authorityType=" + authorityType +
        "}";
    }
}
