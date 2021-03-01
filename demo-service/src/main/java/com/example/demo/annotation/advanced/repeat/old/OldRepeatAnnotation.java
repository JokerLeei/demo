package com.example.demo.annotation.advanced.repeat.old;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 可以进行重复修饰的注解(JDK1.8以前)
 *
 * @author: lijiawei04
 * @date: 2021/2/4 3:27 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface OldRepeatAnnotation {

    String value() default "";

}
