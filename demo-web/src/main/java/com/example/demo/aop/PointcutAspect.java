package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 15:21
 * @description: 不同切入点
 */
@Slf4j
@Aspect
//@Component
public class PointcutAspect {

    @Pointcut(value = "within(com.example.demo.aop.AopController)")
    public void classPointcut() {}

    @Pointcut(value = "@annotation(com.example.demo.aop.AopMethodAnnotation)")
    public void annotationPointcut() {}

    @Pointcut(value = "args(java.lang.String) && execution(public * com.example.demo.aop..*.*(..))")
    public void argsPointcut() {}

    @Pointcut(value = "@args(com.example.demo.aop.AopTypeAnnotation) && execution(public * com.example.demo.aop..*.*(..))")
    public void argsAnnotationPointcut() {}

    @Pointcut(value = "target(com.example.demo.aop.AopController)")
    public void targetPointcut() {}

    @Around(value = "classPointcut()")
    public Object classPointcut(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    @Around(value = "annotationPointcut()")
    public Object annotationPointcut(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@annotation");
        return pjp.proceed();
    }

    @Around(value = "argsPointcut()")
    public Object argsPointcut(ProceedingJoinPoint pjp) throws Throwable {
        log.info("args");
        return pjp.proceed();
    }

    @Around(value = "argsAnnotationPointcut()")
    public Object argsAnnotationPointcut(ProceedingJoinPoint pjp) throws Throwable {
        log.info("@args");
        return pjp.proceed();
    }

    @Around(value = "targetPointcut()")
    public Object targetPointcut(ProceedingJoinPoint pjp) throws Throwable {
        log.info("target");
        return pjp.proceed();
    }
}
