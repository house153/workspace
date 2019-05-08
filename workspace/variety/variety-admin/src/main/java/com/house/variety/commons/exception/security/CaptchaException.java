package com.house.variety.commons.exception.security;

/**
 * 验证码错误异常类
 *
 * @author HuangChao
 */
public class CaptchaException extends SecurityException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("8888","验证码错误");
    }
}
