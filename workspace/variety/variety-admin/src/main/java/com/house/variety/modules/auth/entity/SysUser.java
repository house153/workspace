package com.house.variety.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysUser extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId
    private Long userId;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 权限标识
     */
    private String deptPaths;

    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户类型（00系统用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐加密
     */
    private String salt;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 最后登陆IP
     */
    private String loginIp;
    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 是否短信认证，0:不启用，1:启用
     */
    private Integer smsCheck;
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

    /** 角色组 */
    @TableField(exist = false)
    private Set<Long> roleIds;

    /** 岗位组 */
    @TableField(exist = false)
    private Set<Long> postIds;

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /** 部门信息*/
    @TableField(exist = false)
    private SysDept dept;

    @TableField(exist = false)
    private Boolean smsVaild = false;

    @Override
    public String toString() {
        return "SysUser{" +
        "userId=" + userId +
        ", deptId=" + deptId +
        ", loginName=" + loginName +
        ", userName=" + userName +
        ", userType=" + userType +
        ", email=" + email +
        ", phonenumber=" + phonenumber +
        ", sex=" + sex +
        ", avatar=" + avatar +
        ", password=" + password +
        ", salt=" + salt +
        ", status=" + status +
        ", delFlag=" + delFlag +
        ", loginIp=" + loginIp +
        ", loginDate=" + loginDate +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
