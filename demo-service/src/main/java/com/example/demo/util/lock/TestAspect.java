package com.example.demo.util.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020/11/2 12:14 下午
 */
@Slf4j
@Aspect
@Component
public class TestAspect {

    @Pointcut(value = "@annotation(lock)", argNames = "lock")
    public void testPointcut(Lock lock) {}

    @Around(value = "testPointcut(lock)", argNames = "pjp,lock")
    public Object proceed(ProceedingJoinPoint pjp, Lock lock) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String result = parseElExpression(pjp.getArgs(), method, lock.value(), String.class);
        log.info("test result is:[{}]", result);
        return pjp.proceed();
    }

    private static <T> T parseElExpression(Object[] args, Method method, String elExpression, Class<T> resultType) {
        Parameter[] parameters = method.getParameters();
        StandardEvaluationContext elContext = new StandardEvaluationContext();
        if (parameters != null && parameters.length > 0) {
            // 设置解析变量
            for (int i = 0; i < parameters.length; i++) {
                String paraName = parameters[i].getName();
                Object paraValue = args[i];
                elContext.setVariable(paraName, paraValue);
            }
        }
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(elExpression).getValue(elContext, resultType);
    }

}
