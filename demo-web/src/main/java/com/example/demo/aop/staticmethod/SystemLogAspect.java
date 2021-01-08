package com.example.demo.aop.staticmethod;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * 静态方法的切面
 *
 * @author: lijiawei04
 * @date: 2020/12/15 10:16 上午
 */
@Slf4j
@Aspect
@Component
public class SystemLogAspect extends StaticMethodMatcherPointcutAdvisor {
    
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return aClass.isAnnotationPresent(SystemLog.class);
    }

}
