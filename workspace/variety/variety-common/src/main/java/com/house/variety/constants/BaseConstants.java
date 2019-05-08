package com.house.variety.constants;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:25
 * @Description:
 */
public interface BaseConstants {
    String HEADER_TOKEN = "X-AUTH-TOKEN";
    String SLASH = "/";
    String ASTERISK = "*";

    public interface CACHE_REDIS_KEY {
        String TOKEN_CACHE = "TOKEN:";
        String ROLE_RES = "ROLE-RES";
        String ROLE_RES_PREFIX = "ROLE:";
        String AUTH_USER_CACHE = "userCache";
    }

    public interface ErrorCode {
        String SUCCESS = "0";
        String SYSTEM_FAIL = "8888";
        String PARAMS_FAIL = "1000002";
        String OTHER_FAIL = "1000003";
        String USERINFO_NULL = "20031005";
        String USER_FROZEN = "1002";
    }

    public interface TIME {
        int ONE_HOUR = 3600;
    }

    public interface NUM {
        int ZERO = 0;
        int ONE = 1;
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;
        int SEVEN = 7;
        int EIGHT = 8;
        int NINE = 9;
        int TEN = 10;
        int ONE_HUNDRED = 100;
        int ONE_THOUSAND = 1000;
    }
}