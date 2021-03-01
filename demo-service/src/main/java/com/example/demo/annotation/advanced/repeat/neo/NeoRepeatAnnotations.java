package com.example.demo.annotation.advanced.repeat.neo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  可以进行重复修饰的注解(JDK1.8+)
 *  {@link Repeatable}
 *
 * @author: lijiawei04
 * @date: 2021/2/4 5:34 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeoRepeatAnnotations {

    NeoRepeatAnnotation[] value();

}
