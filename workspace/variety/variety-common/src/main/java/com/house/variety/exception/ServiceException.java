package com.house.variety.exception;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:28
 * @Description:
 */
public class ServiceException extends HslException  {
    public ServiceException() {
    }

    public ServiceException(String code) {
        super(code);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
