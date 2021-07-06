package com.demo.example.util.lock;

import lombok.Value;

/**
 * @author: lijiawei04
 * @date: 2020/10/12 10:44 上午
 * @description: LockVO
 */
@Value
public class LockVO {

    /** key前缀 */
    String group;

    /** key */
    String key;

    /** 加锁失败时的提示信息 */
    String lockFailedMsg;

    /** 等待时长 */
    Long waitTime;

    /** 持有锁时长 */
    Long leaseTime;
}
