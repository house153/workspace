package com.house.variety.commons.constant;

/**
 * Shiro通用常量
 * 
 * @author HuangChao
 */
public interface ShiroConstants
{
    /**
     * 当前登录的用户
     */
    static final String CURRENT_USER = "currentUser";

    /**
     * 用户名
     */
    static final String CURRENT_USERNAME = "username";

    /**
     * 消息key
     */
    static String MESSAGE = "message";

    /**
     * 错误key
     */
    static String ERROR = "errorMsg";

    /**
     * 编码格式
     */
    static String ENCODING = "UTF-8";

    /**
     * 当前在线会话
     */
    String ONLINE_SESSION = "online_session";

    /**
     * 验证码key
     */
    static final String CURRENT_CAPTCHA = "captcha";

    /**
     * 验证码开关
     */
    static final String CURRENT_ENABLED = "captchaEnabled";

    /**
     * 验证码开关
     */
    static final String CURRENT_TYPE = "captchaType";

    /**
     * 验证码
     */
    static final String CURRENT_VALIDATECODE = "validateCode";

    /**
     * 验证码错误
     */
    static final String CAPTCHA_ERROR = "captchaError";

}
