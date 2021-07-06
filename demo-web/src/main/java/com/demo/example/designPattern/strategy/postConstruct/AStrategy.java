package com.demo.example.designPattern.strategy.postConstruct;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 14:27
 * @description:
 */
@Component(value = "AStrategyPlanC")
public class AStrategy implements Strategy {

    @PostConstruct
    public void init() {
        StrategyFactory.register(StrategyTypeEnum.A_STRATEGY.getType(), this);
    }

    @Override
    public void doSomething() {
        System.out.println("A strategy");
    }

}
