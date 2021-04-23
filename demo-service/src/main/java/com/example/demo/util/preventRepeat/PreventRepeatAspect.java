package com.example.demo.util.preventRepeat;

import com.example.demo.util.spring.SpELUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * <h2>防止用户重复请求切面</h2>
 * 配合{@link PreventRepeat}注解使用
 *
 * @author: lijiawei04
 * @date: 2020/11/2 10:43 上午
 */
@Slf4j
@Aspect
@Component
public class PreventRepeatAspect {

    private static final String REDIS_KEY_PREFIX = "gaotu:ketang:coin-mall-server:preventRepeat:";

    @Value("${request.prevent.repeat.default.times:500}")
    private int preventRepeatTimes;

    @Value("${request.prevent.repeat.switch:true}")
    private boolean preventRepeatSwitch;

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    @Pointcut(value = "@annotation(preventRepeat)", argNames = "preventRepeat")
    public void preventRepeatPointcut(PreventRepeat preventRepeat) {}

    @Around(value = "preventRepeatPointcut(preventRepeat)", argNames = "pjp, preventRepeat")
    public Object proceed(ProceedingJoinPoint pjp, PreventRepeat preventRepeat) throws Throwable {
        if (!preventRepeatSwitch) {
            return pjp.proceed();
        }
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        // 获取redis锁key
        String expression = SpELUtils.parseElExpression(pjp.getArgs(), method, preventRepeat.value(), String.class);
        String preventRedisKey = REDIS_KEY_PREFIX + preventRepeat.group() + ":" + expression;
        log.info("PreventRepeatAspect process... originKey is:[{}], parse to redisKey is:[{}]", preventRepeat.value(), preventRedisKey);

        // 限制请求处理
        this.prevent(preventRedisKey, preventRepeat);
        return pjp.proceed();
    }

    /**
     * <h2>限流处理</h2>
     *
     * @param key           锁key
     * @param preventRepeat 限流需要的元数据
     */
    private void prevent(String key, PreventRepeat preventRepeat) {
        long expireTime = preventRepeat.expireTimes();
        if (expireTime == 0) {
            expireTime = preventRepeatTimes;
        }
        TimeUnit expireTimeUnit = preventRepeat.timeUnit();
        Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key,
                                                                            String.valueOf(expireTime),
                                                                            expireTime,
                                                                            expireTimeUnit);
        if (setIfAbsent == null || !setIfAbsent) {
            throw new RuntimeException(preventRepeat.errorMsg());
        }
    }

}
