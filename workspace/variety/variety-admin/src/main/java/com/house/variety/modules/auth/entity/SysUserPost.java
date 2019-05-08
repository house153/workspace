package com.house.variety.modules.auth.entity;

import com.house.variety.modules.BaseEntity;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
@Data
public class SysUserPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;


    @Override
    public String toString() {
        return "SysUserPost{" +
        ", userId=" + userId +
        ", postId=" + postId +
        "}";
    }
}
