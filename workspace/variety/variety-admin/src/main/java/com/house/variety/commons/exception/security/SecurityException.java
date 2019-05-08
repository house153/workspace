package com.house.variety.commons.exception.security;


import com.house.variety.exception.HslException;

/**
 * 用户信息异常类
 *
 * @author HuangChao
 */
public class SecurityException extends HslException {

    private static final long serialVersionUID = 1L;

    public SecurityException(String code, String msessage) {
        super(code, msessage);
    }

    public SecurityException(String code) {
        super(code);
    }

}
