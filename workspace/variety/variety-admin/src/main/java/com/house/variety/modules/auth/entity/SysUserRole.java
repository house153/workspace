package com.house.variety.modules.auth.entity;

import com.house.variety.modules.BaseEntity;
import lombok.Data;


/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;



    @Override
    public String toString() {
        return "SysUserRole{" +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
