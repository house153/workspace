package com.house.variety.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysMenu extends BaseEntity {

    /**
     * 菜单ID
     */
    @TableId
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /** 父菜单名称 */
    @TableField(exist = false)
    private String parentName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

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
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();


}
