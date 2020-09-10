package com.example.demo.designPattern.strategy;

import com.example.demo.designPattern.strategy.postConstruct.Strategy;
import com.example.demo.designPattern.strategy.postConstruct.StrategyFactory;
import com.example.demo.designPattern.strategy.postConstruct.StrategyTypeEnum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 14:43
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyPlanCTest {

    @Test
    public void testPlanC() {
        Strategy strategyA = StrategyFactory.getStrategyByType(StrategyTypeEnum.A_STRATEGY.getType());
        Strategy strategyB = StrategyFactory.getStrategyByType(StrategyTypeEnum.B_STRATEGY.getType());
        strategyA.doSomething();
        strategyB.doSomething();
    }

}
