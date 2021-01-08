package com.example.demo.aop.staticmethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面去切静态方法
 *
 * @author: lijiawei04
 * @date: 2020/12/15 10:15 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SystemLog {

    /**
     * 描述
     */
    String description() default "";

}
