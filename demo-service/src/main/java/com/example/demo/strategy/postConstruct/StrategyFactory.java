package com.example.demo.strategy.postConstruct;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 14:28
 * @description:
 */
@Slf4j
public class StrategyFactory {

    private static Map<Integer, Strategy> STRATEGY_MAP = new ConcurrentHashMap<>();

    public static Strategy getStrategyByType(Integer type) {
        if (!STRATEGY_MAP.containsKey(type)) {
            if (log.isInfoEnabled()) {
                log.info("didn't find strategy of type! type is:{}", type);
            }
        }
        return STRATEGY_MAP.getOrDefault(type, null);
    }

    public static void register(Integer type, Strategy strategy) {
        Assert.notNull(type, "type can't be null!");
        STRATEGY_MAP.put(type, strategy);
    }

}
