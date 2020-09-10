package com.example.demo.designPattern.strategy.applicationContextAware;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 13:36
 * @description:
 */
@Slf4j
@Component
public class StrategyFactory implements ApplicationContextAware {

    private Map<Integer, Strategy> strategyMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Strategy> beanMap = applicationContext.getBeansOfType(Strategy.class);
        if (Objects.nonNull(beanMap)) {
            beanMap.values().forEach((strategy -> {
                this.strategyMap.put(strategy.getType(), strategy);
            }));
        }
    }

    public Strategy getStrategyByType(Integer type) {
        if (!strategyMap.containsKey(type)) {
            if (log.isInfoEnabled()) {
                log.info("didn't find strategy of type! type is:{}", type);
            }
        }
        return strategyMap.getOrDefault(type, null);
    }

    public Map<Integer, Strategy> getStrategyMap() {
        return this.strategyMap;
    }

}
