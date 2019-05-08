package com.house.variety.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.house.variety.modules.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author HuangChao
 * @since 2018-08-27
 */
@Data
public class SysLogininfor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 访问ID
     */
    @TableId
    private Long infoId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private Date loginTime;


}
