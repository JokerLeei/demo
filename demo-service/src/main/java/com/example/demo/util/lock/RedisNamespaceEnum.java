package com.example.demo.util.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lijiawei04
 * @date: 2020/10/12 11:18 上午
 * @description: redis
 */
@Getter
@AllArgsConstructor
public enum RedisNamespaceEnum {

    LOCK(1, "redis锁"),
    ;

    private final Integer type;

    private final String description;

}
