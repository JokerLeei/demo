package com.example.demo.strategy.applicationContextAware;

import org.springframework.stereotype.Component;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 13:40
 * @description:
 */
@Component(value = "AStrategyPlanB")
public class AStrategy implements Strategy {

    @Override
    public Integer getType() {
        return StrategyTypeEnum.A_STRATEGY.getType();
    }

    @Override
    public void doSomething() {
        System.out.println("A strategy");
    }

}
