package com.house.variety.modules.auth.enums;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/10/16 14:29
 * @描述 用户状态
 */
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
