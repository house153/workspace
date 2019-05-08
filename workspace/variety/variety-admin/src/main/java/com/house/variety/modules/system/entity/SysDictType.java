package com.house.variety.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysDictType extends BaseEntity {


    /**
     * 字典主键
     */
    @TableId
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

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
        return "SysDictType{" +
        ", dictId=" + dictId +
        ", dictName=" + dictName +
        ", dictType=" + dictType +
        ", status=" + status +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
