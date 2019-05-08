package com.house.variety.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 系统参数表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysConfig extends BaseEntity {

    /**
     * 参数主键
     */
    @TableId
    private Long configId;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

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
        return "SysConfig{" +
        ", configId=" + configId +
        ", configName=" + configName +
        ", configKey=" + configKey +
        ", configValue=" + configValue +
        ", configType=" + configType +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
