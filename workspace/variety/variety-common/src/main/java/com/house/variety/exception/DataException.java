package com.house.variety.exception;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:27
 * @Description:
 */
public class DataException extends HslException{
    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String code, String message) {
        super(code, message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
