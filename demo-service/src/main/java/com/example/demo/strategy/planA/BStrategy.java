package com.example.demo.strategy.planA;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author: lijiawei04
 * @date: 2020-09-02 14:31
 * @description:
 */
@Service
public class BStrategy implements Strategy, InitializingBean {

    @Override
    public void doSomething() {
        System.out.println("B strategy");
    }

    @Override
    public void afterPropertiesSet() {
        StrategyFactory.register(StrategyTypeEnum.B_STRATEGY.getType(), this);
    }
}
