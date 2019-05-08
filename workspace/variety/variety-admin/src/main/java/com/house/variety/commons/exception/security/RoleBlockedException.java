package com.house.variety.commons.exception.security;

/**
 * 角色锁定异常类
 *
 * @author HuangChao
 */
public class RoleBlockedException extends SecurityException
{

    private static final long serialVersionUID = 1L;

    public RoleBlockedException(String reason)
    {
        super("8888", reason);
    }

}
