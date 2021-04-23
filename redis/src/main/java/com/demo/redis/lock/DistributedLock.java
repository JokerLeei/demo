package com.demo.redis.lock;

/**
 * @author: lijiawei04
 * @date: 2021/4/19 4:12 下午
 */
public interface DistributedLock {

    /**
     * 获得锁
     *
     * @param lockKey 锁key
     * @return        是否成功
     */
    Boolean getLock(String lockKey);

    /**
     * 释放锁
     * @param lockKey 锁key
     * @return        是否成功
     */
    Boolean releaseLock(String lockKey);
}
