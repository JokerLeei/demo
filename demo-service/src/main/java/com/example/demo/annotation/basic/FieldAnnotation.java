package com.example.demo.annotation.basic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijiawei04
 * @date: 2020-09-10 13:39
 * @description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAnnotation {

    /**
     * 定义注解的一个元素 并给定默认值
     * @return value
     */
    String value() default "我是定义在[属性]上的注解元素value的默认值";

}
