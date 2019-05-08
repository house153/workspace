package com.house.variety.commons.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/23 11:16
 * @描述 重写QueryWrapper
 */
public class FrameQueryWrapper<T>  extends QueryWrapper<T> {

    @Override
    public FrameLambdaQueryWrapper<T> lambda() {
        return new FrameLambdaQueryWrapper<>(entity, paramNameSeq, paramNameValuePairs, expression);
    }
}
