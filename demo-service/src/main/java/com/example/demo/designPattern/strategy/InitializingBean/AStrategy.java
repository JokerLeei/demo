package com.example.demo.designPattern.strategy.InitializingBean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author: lijiawei04
 * @date: 2020-09-02 14:28
 * @description:
 */
@Component(value = "AStrategyPlan")
public class AStrategy implements Strategy, InitializingBean {

    @Override
    public void doSomething() {
        System.out.println("A strategy");
    }

    @Override
    public void afterPropertiesSet() {
        StrategyFactory.register(StrategyTypeEnum.A_STRATEGY.getType(), this);
    }

}
