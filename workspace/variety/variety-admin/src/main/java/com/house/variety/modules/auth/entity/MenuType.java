package com.house.variety.modules.auth.entity;

/**
 * @创建者 HuangChao
 * @归属项目 tesla
 * @创建时间 2018/12/21 13:02
 * @描述 菜单类型 枚举
 */
public enum MenuType {
    CATALOG("M", "目录"), MENU("C", "菜单"), BUTTON("F", "按钮");

    private final String code;
    private final String info;

    MenuType(String code, String info) {
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
