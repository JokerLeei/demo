package com.example.demo.designPattern.strategy;

import com.example.demo.designPattern.strategy.InitializingBean.Strategy;
import com.example.demo.designPattern.strategy.InitializingBean.StrategyFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author: lijiawei04
 * @date: 2020-09-02 20:37
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyPlanATest {

    @Test
    public void testPlanA() {
        Strategy a = StrategyFactory.getByType(1);
        a.doSomething();
        Strategy b = StrategyFactory.getByType(2);
        b.doSomething();
    }

}
