package com.demo.example.designPattern.strategy.applicationContextAware;

import org.springframework.stereotype.Component;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 13:40
 * @description:
 */
@Component(value = "BStrategyPlanB")
public class BStrategy implements Strategy {

    @Override
    public Integer getType() {
        return StrategyTypeEnum.B_STRATEGY.getType();
    }

    @Override
    public void doSomething() {
        System.out.println("B strategy");
    }

}
