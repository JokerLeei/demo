package com.demo.example.util.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h2 color="orange">分布式锁注解</h2>
 * 锁粒度: 方法<br>
 * 注解解析器: {@link LockAspect}
 *
 * @author: lijiawei04
 * @date: 2020/10/10 11:38 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lock {

    /**
     * 锁分组名称，用于避免重复key
     */
    String group() default "redis_lock_order";

    /**
     * key表达式
     */
    String value() default "";

    /**
     * 获取锁失败时候的提示信息
     */
    String lockFailedMsg() default "";

    /**
     * 等待时间(ms) 默认0即获取不到锁立即返回
     * @return 等待时间(ms)
     */
    long waitTime() default 0;

    /**
     * 最大持有锁时间，如果超过该时间仍未主动释放，将自动释放锁
     * @return 最大持有锁时间(ms)
     */
    long leaseTime() default 3000;

}
