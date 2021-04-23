package com.demo.mybatis.config.dynamic.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态切换datasource切面 {@link DataSource}
 * 设置@Order(1): 使切换数据源AOP先于spring事务AOP, 若顺序颠倒则切换数据源无效
 *
 * @author: lijiawei04
 * @date: 2021/4/14 2:50 下午
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {

    /**
     * ElementType.TYPE：注在类上，通过@annotation方式拿不到代理对象，但是通过包扫描能拿到
     * ElementType.METHOD：注在方法上，直接转为MethodSignature获取方法上注解
     */
    @Pointcut(value = "execution(public * com.demo.mybatis.mapper..*.*(..))")
    public void datasourcePointcut() {}

    @Before(value = "datasourcePointcut()")
    public void before(JoinPoint jp) {
        // 处理ElementType.TYPE
        processType(jp);

        // 处理ElementType.METHOD
        processMethod(jp);
    }

    private void processType(JoinPoint jp) {
        // 这里拿到的是MyBatis mapper接口的代理对象MapperProxy
        Class<?> targetClazz = jp.getTarget().getClass();

        for (Class<?> clazz : targetClazz.getInterfaces()) {
            DataSource dataSource = clazz.getAnnotation(DataSource.class);
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DynamicDatasourceHolder.setDataSource(dataSource.value());
                log.info("clazz:[{}] use dataSource:[{}]", clazz.getName(), dataSource.value());
            }
        }
    }

    private void processMethod(JoinPoint jp) {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        DataSource[] annotations = method.getAnnotationsByType(DataSource.class);
        for (DataSource annotation : annotations) {
            DynamicDatasourceHolder.setDataSource(annotation.value());
            log.info("method:[{}] use dataSource:[{}]", getMethodName(jp), annotation.value());
        }
    }

    private String getMethodName(JoinPoint jp) {
        return jp.getSignature() + "." + jp.getSignature().getName() + "(...)";
    }

    @After(value = "datasourcePointcut()")
    public void after() {
        DynamicDatasourceHolder.clearDataSource();
    }

}
