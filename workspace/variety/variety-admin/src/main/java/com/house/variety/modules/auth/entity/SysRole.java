package com.house.variety.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysRole extends BaseEntity {

    /**
     * 角色ID
     */
    @TableId
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

//    @TableField(exist = false)
//    private List<SysRoleMenu> menus;

    /** 用户是否存在此角色标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    /**
     * 归属运营商
     */
    private Long deptId;

    /**
     * 归属运营商名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 权限标识
     */
    private String deptPaths;

    @Override
    public String toString() {
        return "SysRole{" +
                ", roleId=" + roleId +
                ", roleName=" + roleName +
                ", roleKey=" + roleKey +
                ", roleSort=" + roleSort +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", remark=" + remark +
                "}";
    }
}
