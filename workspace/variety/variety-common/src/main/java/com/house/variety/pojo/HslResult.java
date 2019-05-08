package com.house.variety.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: tanfan
 * @Date: 2019/5/8 14:30
 * @Description:
 */
public class HslResult<T> implements Serializable {
    private static final long serialVersionUID = 5640399479180329748L;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public HslResult() {
    }

    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, new Class[]{beanType});

        try {
            List<T> list = (List)MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
