package com.house.variety.commons.exception.security;

/**
 * 用户错误最大次数异常类
 *
 * @author HuangChao
 */
public class UserPasswordRetryLimitExceedException extends SecurityException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount)
    {
        super("8888", "用户达到错误次数[" + retryLimitCount+"]");
    }
}
