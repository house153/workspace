package com.house.variety.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 系统部门表
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Data
public class SysDept extends BaseEntity {

    /**
     * 部门id
     */
    @TableId
    private Long deptId;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 父部门名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
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
     * 类型
     */
    private Integer type;

    /**
     * 扩展字段A
     */
    private String extendA;

    /**
     * 扩展字段B
     */
    private String extendB;


    @Override
    public String toString() {
        return "SysDept{" +
        ", deptId=" + deptId +
        ", parentId=" + parentId +
        ", ancestors=" + ancestors +
        ", deptName=" + deptName +
        ", orderNum=" + orderNum +
        ", leader=" + leader +
        ", phone=" + phone +
        ", email=" + email +
        ", status=" + status +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        "}";
    }
}
