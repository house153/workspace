package com.house.variety.commons.exception.security;

/**
 * 用户不存在异常类
 *
 * @author HuangChao
 */
public class UserNotExistsException extends SecurityException
{

    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("8888","用户不存在");
    }
}
