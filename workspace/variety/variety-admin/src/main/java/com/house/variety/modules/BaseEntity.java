package com.house.variety.modules;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.house.variety.util.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/9/6 9:36
 * @描述 Entity基类
 */
@Data
public class BaseEntity implements Serializable  {

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    /**排序字段*/
    @TableField(exist = false)
    private String orderByColumn;

    /**排序方式,ASC、DESC*/
    @TableField(exist = false)
    private String order;

    @JsonIgnore
    public Boolean isAsc() {
        return StringUtils.isBlank(order) || StringUtils.equalsIgnoreCase(order,"asc");
    }
}
