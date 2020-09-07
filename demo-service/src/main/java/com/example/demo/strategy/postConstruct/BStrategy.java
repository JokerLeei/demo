package com.example.demo.strategy.postConstruct;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 14:35
 * @description:
 */
@Component(value = "BStrategyPlanC")
public class BStrategy implements Strategy {

    @PostConstruct
    public void init() {
        StrategyFactory.register(StrategyTypeEnum.B_STRATEGY.getType(), this);
    }

    @Override
    public void doSomething() {
        System.out.println("B strategy");
    }
}
