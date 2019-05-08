package com.house.variety.modules.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.variety.modules.auth.entity.SysPost;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HuangChao
 * @since 2018-09-06
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    List<SysPost> selectPostByUserId(Long userId);
}
