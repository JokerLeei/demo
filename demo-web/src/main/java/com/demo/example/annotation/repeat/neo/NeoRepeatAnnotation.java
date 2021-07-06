package com.demo.example.annotation.repeat.neo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以进行重复修饰的注解(JDK1.8+)
 * {@link Repeatable}
 *
 * @author: lijiawei04
 * @date: 2021/2/4 5:13 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NeoRepeatAnnotations.class)
public @interface NeoRepeatAnnotation {

    String value() default "";

}
