package com.demo.example.annotation.basic;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijiawei04
 * @date: 2020-09-09 10:04
 * @description: 自定义注解(类、接口、枚举)
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TypeAnnotation {

    /**
     * 定义注解的一个元素 并给定默认值
     * @return value
     */
    String value() default "我是定义在[类接口枚举类]上的注解元素value的默认值";

}
