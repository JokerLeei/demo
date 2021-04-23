package com.demo.redis.lock;


import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于redis分布式锁
 *
 * @author: lijiawei04
 * @date: 2021/4/19 4:13 下午
 */
@Slf4j
@Component
public class DistributedLockImpl implements DistributedLock {

    @Value("${gaotu.redis.key.prefix:}")
    private String redisPrefix;

    @Value("${gaotu.redis.key.addTimeOut:300}")
    private Long getKeyTimeout;

    @Value("${gaotu.redis.key.existTimeOut:259200}")
    private Long keyExistTimeout;

    @Resource
    private RedisTemplate<String, String> lockRedisStringTemplate;

    private static final String KEY = "lock";

    private static final String FAILED = "1";

    private static final String ADD_KEY_LUA_SCRIPT =
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 1 else return 0 end";

    private static final String DEL_KEY_LUA_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private static final RedisScript<Boolean> SET_IF_ABSENT_SCRIPT =
            new DefaultRedisScript<>(ADD_KEY_LUA_SCRIPT, Boolean.class);

    private static final RedisScript<Boolean> DEL_KEY_SCRIPT =
            new DefaultRedisScript<>(DEL_KEY_LUA_SCRIPT, Boolean.class);

    @Override
    public Boolean getLock(String lockValue) {
        long start = System.currentTimeMillis();
        final long end = start + getKeyTimeout;
        boolean res = Boolean.FALSE;
        try {
            while (lock(lockValue)) {
                if (System.currentTimeMillis() > end) {
                    throw new Exception("获取锁超时");
                } else {
                    res = Boolean.TRUE;
                    log.info("redis获取锁成功, value: {}, requestId: {}", lockValue, lockValue);
                }
            }
        } catch (Exception e) {
            log.error("redis获取锁失败, value: {}, requestId: {}, error: {}", lockValue, lockValue, e);
        }
        return res;
    }

    @Override
    public Boolean releaseLock(String lockValue) {
        long start = System.currentTimeMillis();
        final long end = start + getKeyTimeout;
        boolean res = Boolean.FALSE;
        try {
            while (unlock(lockValue)) {
                if (System.currentTimeMillis() > end) {
                    throw new Exception("释放锁超时");
                } else {
                    res = Boolean.TRUE;
                    log.info("redis解锁成功, value: {}, requestId: {}", lockValue, lockValue);
                }
            }
        } catch (Exception e) {
            log.error("redis解锁失败, value: {}, requestId: {}, error: {}", lockValue, lockValue, e);
        }
        return res;
    }

    private Boolean lock(String lockValue) {
        List<String> keys = Lists.newArrayListWithCapacity(1);
        keys.add(redisPrefix + KEY);
        Object[] args = {lockValue, keyExistTimeout.toString()};
        return lockRedisStringTemplate.execute(SET_IF_ABSENT_SCRIPT, keys, args);
    }

    private Boolean unlock(String lockValue) {
        List<String> keys = Lists.newArrayListWithCapacity(1);
        keys.add(redisPrefix + KEY);
        Object[] args = {lockValue, keyExistTimeout.toString()};
        return lockRedisStringTemplate.execute(DEL_KEY_SCRIPT, keys, args);
    }

}
