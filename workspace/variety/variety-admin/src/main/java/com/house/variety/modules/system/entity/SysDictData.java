package com.house.variety.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysDictData extends BaseEntity {

    /**
     * 字典编码
     */
    @TableId
    private Long dictCode;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性
     */
    private String cssClass;

    /**
     * 回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 状态（0正常 1停用）
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

    @Override
    public String toString() {
        return "SysDictData{" +
        ", dictCode=" + dictCode +
        ", dictSort=" + dictSort +
        ", dictLabel=" + dictLabel +
        ", dictValue=" + dictValue +
        ", dictType=" + dictType +
        ", cssClass=" + cssClass +
        ", listClass=" + listClass +
        ", isDefault=" + isDefault +
        ", status=" + status +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
