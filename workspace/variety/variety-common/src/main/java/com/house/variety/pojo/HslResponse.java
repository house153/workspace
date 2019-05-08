package com.house.variety.pojo;

import com.house.variety.exception.HslException;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:30
 * @Description:
 */
public class HslResponse<T> implements Serializable {
    private String code;
    private T data;
    private String msg;

    public HslResponse() {
    }

    public HslResponse(String errorCode) {
        errorCode = (String)Optional.ofNullable(errorCode).orElse("8888");
        this.code = errorCode;
    }

    public static <T> HslResponse<T> ok(T data) {
        return restResult(data, "0");
    }

    public static <T> HslResponse<Object> failed(String msg) {
        return restResult((Object)null, "8888", msg);
    }

    public static <T> HslResponse<Object> failed(String code, String msg) {
        return restResult((Object)null, code, msg);
    }

    public static <T> HslResponse<T> restResult(T data, String code) {
        return restResult(data, code, code);
    }

    private static <T> HslResponse<T> restResult(T data, String code, String msg) {
        HslResponse<T> apiResult = new HslResponse();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean ok() {
        return "0".equals(this.code);
    }

    public T serviceData() {
        if (!this.ok()) {
            throw new HslException(this.msg);
        } else {
            return this.data;
        }
    }

    public String getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HslResponse)) {
            return false;
        } else {
            HslResponse<?> other = (HslResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label42: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label42;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label42;
                    }

                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof HslResponse;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        return result;
    }

    public String toString() {
        return "HslResponse(code=" + this.getCode() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }
}
