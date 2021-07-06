//package com.example.demo.util.lock;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.concurrent.TimeUnit;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 锁切面, 配合{@link Lock}使用
// *
// * @author: lijiawei04
// * @date: 2020/10/10 11:48 上午
// */
//@Slf4j
//@Aspect
//@Order(value = 1)
//@Component
//public class LockAspect {
//
//    private final RedisTemplate<String, String> redisTemplate;
//
//    private static final String LOCK_KEY_PREFIX = "lock:key:prefix:";
//
//    @Pointcut(value = "@annotation(lock)")
//    public void lockPointcut(Lock lock) {
//    }
//
//    @Around(value = "lockPointcut(lock)", argNames = "pjp,lock")
//    public Object process(ProceedingJoinPoint pjp, Lock lock) throws Throwable {
//        log.info("start lock...");
//
//        // 解析注解数据
//        LockVO lockVo = this.parseLockAnnotationData(pjp, lock);
//
//        // 生成锁key
//        String lockKey = LOCK_KEY_PREFIX + lockVo.getGroup() + ":" + lockVo.getKey();
//
//        // 竞争分布式锁 [加锁时生成的唯一的lockId用于保证加解锁是同一个线程]
//        String lockId = this.lock(lockVo, lockKey);
//        try {
//            return pjp.proceed();
//        } finally {
//            // 释放锁
//            this.unlock(lockKey, lockId);
//        }
//    }
//
//    private String lock(LockVO lockVo, String lockKey) {
//        // 具体加锁逻辑可以自己选择，推荐使用redisson，也可以选择自己实现。
//        // 自己实现需要考虑满足可重入性、锁超时等问题。
//        // 目前没有完美的分布式锁实现，需要根据自己的项目的应用场景做出权衡和选择。
//
//        // 以下介绍自己实现逻辑的大致思路
//        // 1. 如果当前线程已经拿到锁，直接返回
//        // 可以通过ThreadLocal实现可重入式分布式锁，具体实现省略。
//
//        // 2. 否则，获取reds分布式锁，拿到新的lockId
//        Long waitTime = lockVo.getWaitTime();
//        Long leaseTime = lockVo.getLeaseTime();
//
//        if (waitTime > 0) {
//            // 基于set命令实现，指定key失效时间，如果未获取到锁，则等待若干ms后重试，
//            // 在waitTime过后仍未获取到锁则获取锁失败。
//            lockId = RedisLockUtils.tryLock(lockKey, waitTime, leaseTime, TimeUnit.MILLISECONDS);
//            if (lockId == null) {
//                log.error("WARN:延迟加锁失败, 数据可能出现问题, 锁key:[{}], 锁数据[{}]", lockKey, lockVo);
//            }
//        } else {
//            // 基于set命令实现，指定key失效时间，如果未获取到锁。则加锁失败。
//            lockId = RedisLockUtils.tryLock(lockKey, leaseTime, TimeUnit.MILLISECONDS);
//        }
//        // 未拿到锁
//        if (lockId == null) {
//            throw new RuntimeException(lockVo.getLockFailedMsg());
//        }
//        // 放入ThreadLocal，用于实现可重入性
//        return lockId;
//    }
//
//    private void unlock(String lockKey, String lockId) {
//        // 释放分布式锁
//        if (ReentrantUtil.canRemove(lockKey)) {
//            ReentrantUtil.remove(lockKey);
//            // 如果lockId代表的锁依然存在，则可以解锁成功。
//            Boolean success = RedisLockUtils.unlock(lockKey, lockId);
//            if (!success) {
//                // 锁超时情况下会出现该问题，当出现该问题时，需要根据情况特殊处理。
//                log.error("释放锁失败, lockKey:[{}], lockId:[{}]", lockKey, lockId);
//            }
//        } else {
//            ReentrantUtil.release(lockKey);
//        }
//    }
//
//    /**
//     * 解析@Lock数据
//     */
//    private LockVO parseLockAnnotationData(ProceedingJoinPoint pjp, Lock lock) {
//        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
//
//        String keyExpression = lock.value();
//        // 解析el表达式，获取锁key
//        String key = parseElExpression(pjp.getArgs(), method, keyExpression, String.class);
//        return new LockVO(lock.group(), key, lock.lockFailedMsg(), lock.waitTime(), lock.leaseTime());
//    }
//
//    /**
//     * 解析EL表达式
//     *
//     * @param args         方法参数
//     * @param method       方法
//     * @param elExpression EL表达式
//     * @param resultType   结果类型
//     * @param <T>          结果类型
//     *
//     * @return 结果
//     */
//    private static <T> T parseElExpression(Object[] args, Method method, String elExpression, Class<T> resultType) {
//        Parameter[] parameters = method.getParameters();
//        StandardEvaluationContext elContext = new StandardEvaluationContext();
//        if (parameters != null && parameters.length > 0) {
//            // 设置解析变量
//            for (int i = 0; i < parameters.length; i++) {
//                String paraName = parameters[i].getName();
//                Object paraValue = args[i];
//                elContext.setVariable(paraName, paraValue);
//            }
//        }
//        ExpressionParser parser = new SpelExpressionParser();
//        return parser.parseExpression(elExpression).getValue(elContext, resultType);
//    }
//
//    @Autowired
//    public LockAspect(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//}
