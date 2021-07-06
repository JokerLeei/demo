package com.demo.example.annotation.repeat.old;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以进行重复修饰的注解(JDK1.8以前)
 * {@link OldRepeatAnnotation}
 *
 * @author: lijiawei04
 * @date: 2021/2/4 4:47 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OldRepeatAnnotations {

    /**
     * {@link OldRepeatAnnotation}
     */
    OldRepeatAnnotation[] value();

}
