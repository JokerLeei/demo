package com.demo.rocketmq.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link RocketListener}
 *
 * @author: lijiawei04
 * @date: 2021/5/31 1:51 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RocketListeners {

    /**
     * {@link RocketListener}
     *
     * @return RocketListener[]
     */
    RocketListener[] value();

}
