package com.example.demo.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 14:43
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopMethodAnnotation {

    /**
     * @return value
     */
    String value() default "AopMethodAnnotation";
}
