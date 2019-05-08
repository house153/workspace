package com.house.variety.commons.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.google.common.base.CaseFormat;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.baomidou.mybatisplus.core.enums.SqlKeyword.*;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/23 11:17
 * @描述 重写LambdaQueryWrapper
 */
public class FrameLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {


    public FrameLambdaQueryWrapper() {
        super(null);
    }

    public FrameLambdaQueryWrapper(T entity) {
        super(entity);
    }

    @SuppressWarnings(value = "unchecked")
    FrameLambdaQueryWrapper(T entity, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs,
                         MergeSegments mergeSegments) {
        this.entity = entity;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.initEntityClass();
    }

    public LambdaQueryWrapper<T> orderBy(boolean condition, boolean isAsc, String... columns) {
        if(!condition) {
            return typedThis;
        }
        if (ArrayUtils.isEmpty(columns)) {
            return typedThis;
        }
        SqlKeyword mode = isAsc ? ASC : DESC;
        for (String field : columns) {
            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,field);
            doIt(condition, ORDER_BY, () -> column, mode);
        }
        return typedThis;
    }
}
