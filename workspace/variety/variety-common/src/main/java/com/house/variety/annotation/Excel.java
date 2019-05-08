package com.house.variety.annotation;


import com.house.variety.enums.DataType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Excel {
    String name();

    String prompt() default "";

    String[] combo() default {};

    boolean isExport() default true;

    DataType type() default DataType.OTHER;
}
