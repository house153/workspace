package com.house.variety.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:38
 * @Description:
 */
public class BeanConvertUtils {
    public BeanConvertUtils() {
    }

    public static Map<String, Object> bean2Map(Object obj) {
        try {
            Map<String, Object> map = BeanUtils.describe(obj);
            map.remove("class");
            return map;
        } catch (IllegalAccessException var2) {
            var2.printStackTrace();
        } catch (InvocationTargetException var3) {
            var3.printStackTrace();
        } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
        }

        return null;
    }



    public static void copyProperties(Object dest, Object src) {
        if (src != null && dest != null) {
            try {
                BeanUtils.copyProperties(dest, src);
            } catch (IllegalAccessException var3) {
                var3.printStackTrace();
            } catch (InvocationTargetException var4) {
                var4.printStackTrace();
            } catch (IllegalArgumentException var5) {
                var5.printStackTrace();
            }

        }
    }

    static {
        ConvertUtils.register(new DateTimeConverter(), Date.class);
        ConvertUtils.register(new DateTimeConverter(), java.sql.Date.class);
        ConvertUtils.register(new LongConverter((Object)null), Long.class);
        ConvertUtils.register(new ShortConverter((Object)null), Short.class);
        ConvertUtils.register(new IntegerConverter((Object)null), Integer.class);
        ConvertUtils.register(new DoubleConverter((Object)null), Double.class);
        ConvertUtils.register(new BigDecimalConverter((Object)null), BigDecimal.class);
    }
}
