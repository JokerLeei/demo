package com.demo.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 20:46
 * @description:
 */
@Slf4j
@Aspect
//@Component
public class BindAspect {

    @Around(value = "@annotation(aopMethodAnnotation)", argNames = "pjp,aopMethodAnnotation")
    public Object around1(ProceedingJoinPoint pjp, AopMethodAnnotation aopMethodAnnotation) throws Throwable {
        String value = aopMethodAnnotation.value();
        log.info("aopMethodAnnotation value :{}", value);
        return pjp.proceed();
    }

    @Around(value = "args(arg1, arg2) && within(com.demo.example.aop.AopController)", argNames = "pjp,arg1,arg2")
    public Object around2(ProceedingJoinPoint pjp, String arg1, Integer arg2) throws Throwable {
        log.info("arg1 is:{}", arg1);
        log.info("arg2 is:{}", arg2);
        return pjp.proceed();
    }
}
