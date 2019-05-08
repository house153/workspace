package com.house.variety.modules.system.entity;

import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
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

}
