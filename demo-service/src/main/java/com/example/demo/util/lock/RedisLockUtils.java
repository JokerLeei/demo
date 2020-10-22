package com.example.demo.util.lock;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * <h2 color="orange">RedisLockUtils</h2>
 * redis实现分布式锁的工具类
 *
 * @author: lijiawei04
 * @date: 2020/10/13 6:57 下午
 */
@Slf4j
@Component
public class RedisLockUtils {

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        RedisLockUtils lockUtils = this;
        lockUtils.redissonClient = this.redissonClient;
    }

}
