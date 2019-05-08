package com.house.variety.commons.exception.security;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author HuangChao
 */
public class UserPasswordNotMatchException extends SecurityException
{

    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("8888","用户名或密码错误");
    }
}
