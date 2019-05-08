package com.house.variety.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 岗位实体
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Data
public class SysPost extends BaseEntity {

    /**
     * 岗位ID
     */
    @TableId
    private Long postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer postSort;

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


    /** 用户是否存在此岗位标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "SysPost{" +
        ", postId=" + postId +
        ", postCode=" + postCode +
        ", postName=" + postName +
        ", postSort=" + postSort +
        ", status=" + status +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
