package com.example.demo.strategy;

import com.example.demo.strategy.applicationContextAware.Strategy;
import com.example.demo.strategy.applicationContextAware.StrategyFactory;
import com.example.demo.strategy.applicationContextAware.StrategyTypeEnum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 13:57
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyPlanBTest {

    @Resource
    private StrategyFactory strategyFactory;

    @Test
    public void testPlanB() {
        Strategy strategyA = strategyFactory.getStrategyByType(StrategyTypeEnum.A_STRATEGY.getType());
        Strategy strategyB = strategyFactory.getStrategyByType(StrategyTypeEnum.B_STRATEGY.getType());

        strategyA.doSomething();
        strategyB.doSomething();
    }

}

