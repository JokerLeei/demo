package com.demo.example.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <h2>非spring管理的类使用spring容器中注入的bean</h2>
 * <font color="orange">原理</font>: 在spring容器初始化时候copy一份applicationContext, 非spring管理的类可以从中获取bean<br>
 * <strong>需要加{@link Component}注解并且实现{@link ApplicationContextAware}</strong>
 *
 * @author: lijiawei04
 * @date: 2020/10/14 4:58 下午
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        Assert.notNull(applicationContext, "application can't be null!");
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
