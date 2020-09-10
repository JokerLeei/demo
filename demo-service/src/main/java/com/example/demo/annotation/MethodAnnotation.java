package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijiawei04
 * @date: 2020-09-10 13:38
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnnotation {

    /**
     * 定义注解的一个元素 并给定默认值
     * @return value
     */
    String value() default "我是定义在[方法]上的注解元素value的默认值";

}
