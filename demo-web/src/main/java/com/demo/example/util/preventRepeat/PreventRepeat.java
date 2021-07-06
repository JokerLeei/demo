package com.demo.example.util.preventRepeat;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <h1>防止重复请求annotation</h1>
 * 配合{@link PreventRepeatAspect}使用
 *
 * @author: lijiawei04
 * @date: 2020/11/2 10:43 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreventRepeat {

    /**
     * 锁key表达式
     */
    String value() default "";

    /**
     * 锁分组name
     */
    String group() default "prevent_repeat_group";

    /**
     * 限制时间限制, 超过此时间后即可再次发起请求
     * 如果不配置(为0), 则取apollo中 {request.prevent.repeat.default.times} 默认值
     */
    long expireTimes() default 0L;

    /**
     * 限制时间的单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 重复请求时的报错信息
     */
    String errorMsg() default "您的手速太快啦～ 请稍后重试";

}
