package com.house.variety.seq;


import java.util.UUID;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:04
 * @Description:
 */
public class IdUtil {
    private static IdGenerator idGenerator = new CommonSelfIdGenerator();

    public IdUtil() {
    }

    public static synchronized long getId() {
        return idGenerator.generateId().longValue();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; ++i) {
            System.out.println(getId());
        }

    }
}
