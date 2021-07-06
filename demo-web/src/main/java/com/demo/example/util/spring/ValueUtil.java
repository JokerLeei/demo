package com.demo.example.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取spring容器中的配置数据(类似@Value注解)
 *
 * @author: lijiawei04
 * @date: 2021/3/8 10:47 上午
 */
@Slf4j
@Component
public class ValueUtil implements BeanFactoryAware {

    private static BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

    private static BeanExpressionContext expressionContext;

    private static BeanFactory beanFactory;

    /**
     * 解析spring容器中的一个表达式，获取一个值
     * 1.根据 ${} 获取spring容器中值
     * 2.根据 #{} 作运算，得结果
     *
     * @param value 一个固定值或一个表达式。如果是一个固定值，则直接返回固定值，否则解析一个表达式，返回解析后的值
     * @return      表达式值
     */
    public static Object resolveExpression(String value) {
        String resolvedValue = resolve(value);

        if (!(resolvedValue.startsWith("#{") && resolvedValue.endsWith("}"))) {
            return resolvedValue;
        }

        Object result = resolver.evaluate(resolvedValue, expressionContext);
        log.info("resolveExpression expression is:{}, resolve result is:{}", value, result);
        return result;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ValueUtil.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
            expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
        }
    }

    /**
     * Resolve the specified value if possible.
     * @param value the value to resolve
     * @return the resolved value
     * @see ConfigurableBeanFactory#resolveEmbeddedValue
     */
    private static String resolve(String value) {
        if (beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }

}

