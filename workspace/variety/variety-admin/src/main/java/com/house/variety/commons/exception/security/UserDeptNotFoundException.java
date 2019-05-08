package com.house.variety.commons.exception.security;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/12/25 14:08
 * @描述 用户部门异常类
 */
public class UserDeptNotFoundException extends SecurityException {


    private static final long serialVersionUID = 1L;

    public UserDeptNotFoundException() {
        super("8888", "用户所属部门不存在或状态异常!");
    }
}
