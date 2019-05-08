package com.house.variety.commons.exception.security;

/**
 * 用户错误记数异常类
 *
 * @author HuangChao
 */
public class UserPasswordRetryLimitCountException extends SecurityException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount, String password) {
        super("8888", "用户达到错误计数，次数:[" + retryLimitCount+"],密码:["+password+"]");
    }
}
