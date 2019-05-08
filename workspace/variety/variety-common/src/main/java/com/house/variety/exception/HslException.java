package com.house.variety.exception;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:28
 * @Description:
 */
public class HslException extends RuntimeException  {
    protected String code;

    public HslException() {
    }

    public HslException(String code, String message) {
        super(message);
        this.code = code;
    }

    public HslException(String code) {
        super(code);
    }

    public HslException(String message, Throwable cause) {
        super(message, cause);
    }

    public HslException(Throwable cause) {
        super(cause);
    }

    public HslException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
