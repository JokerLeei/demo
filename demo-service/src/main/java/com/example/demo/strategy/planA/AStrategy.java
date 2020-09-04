package com.example.demo.strategy.planA;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author: lijiawei04
 * @date: 2020-09-02 14:28
 * @description:
 */
@Service
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
