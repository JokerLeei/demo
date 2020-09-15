package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 19:00
 * @description: @Pointcut注解中的argNames
 */
@Slf4j
@Aspect
@Component
public class ArgNamesAspect {

    @Pointcut(value = "args(p2, p1) && within(com.example.demo.aop.AopController)", argNames = "p2,p1")
    public void pc(String p2, String p1) {}

    @Around(value = "pc(arg1, arg2)", argNames = "pjp,arg1,arg2")
    public Object argNamesAround(ProceedingJoinPoint pjp, String arg1, String arg2) throws Throwable {
        log.info("arg1 is:{}", arg1);
        log.info("arg2 is:{}", arg2);
        return pjp.proceed();
    }
}
