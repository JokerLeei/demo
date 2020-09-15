package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-10 17:37
 * @description: 不同advice类型
 */
@Slf4j
@Aspect
//@Component
public class AdviceAspect {

    @Pointcut("execution(public * com.example.demo.aop..*.*(..))")
    public void pointcut() {}

    @Before(value = "pointcut()")
    public void before(JoinPoint jp) {
        log.warn("[BEFORE]");
        // 获得请求
        HttpServletRequest request = getHttpServletRequest();

        // 记录下请求内容
        log.info("[URL] : {}", request.getRequestURL().toString());
        log.info("[HTTP_METHOD] : {}", request.getMethod());
        log.info("[IP] : {}", request.getRemoteAddr());
        log.info("[THE ARGS OF THE CONTROLLER] : {}", Arrays.toString(jp.getArgs()));

        // 记录下调用的方法所在类和方法名
        log.info("[METHOD] : {}", jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "()");
    }

    @After(value = "pointcut()")
    public void after(JoinPoint jp) {
        log.warn("[AFTER]");

        log.info("[METHOD] : {}", jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "()");
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.warn("[AROUND]");

        log.info("[METHOD] : {}", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + "()");
        return pjp.proceed();
    }

    @AfterReturning(pointcut = "pointcut()", returning = "returnObj")
    public void afterReturning(JoinPoint jp, Object returnObj){
        log.warn("[AFTER RETURNING]");

        log.info("[METHOD] : {}", jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "()");
        log.info("[RETURN VALUE] : {}", returnObj);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Exception ex) {
        log.warn("[AFTER THROWING]");

        log.info("[METHOD] : {}", jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "()");
        log.info("[EXCEPTION] : {}", ex.getMessage());
    }

    private static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }
}
