package com.house.variety.modules.auth.enums;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/12/19 17:14
 * @描述 部门类型 枚举
 */
public enum DeptType {
    DEPT(0, "部门"), OPERATORS(1, "运营商");

    private final Integer code;
    private final String info;

    DeptType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
