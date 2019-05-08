package com.house.variety.commons.exception.security;

/**
 * 用户锁定异常类
 *
 * @author HuangChao
 */
public class UserBlockedException extends SecurityException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException(String reason)
    {
        super("8888", reason);
    }
}
