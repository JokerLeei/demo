package com.auto.rocketmq.annotation;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.shade.com.google.common.collect.Lists;
import com.auto.rocketmq.annotation.config.RocketConfig;
import com.auto.rocketmq.annotation.constant.RocketConstant;
import com.auto.rocketmq.annotation.util.RocketPropertiesUtil;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * RocketMQ consumer/endpoint 注册管理器
 *
 * @author: lijiawei04
 * @date: 2021/3/15 5:27 下午
 */
@Slf4j
public class RocketRegistrar {

    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();
    private BeanExpressionContext expressionContext;
    private BeanFactory beanFactory;

    private RocketConfig rocketConfig;

    /**
     * 存储所有的consumer
     */
    private List<Consumer> consumerList = Lists.newArrayList();

    /**
     * 存储所有的consumer端点
     */
    private final List<RocketListenerEndpoint> endpointList = new ArrayList<>();

    /**
     * 注册consumer端点
     * @param endpoint consumer端点
     */
    public void registerEndpoint(RocketListenerEndpoint endpoint) {
        synchronized (endpointList) {
            endpointList.add(endpoint);
        }
    }

    public void registerConsumer(String groupId, String messageModel, List<RocketListenerEndpoint> endpointList) {
        Properties properties = RocketPropertiesUtil.getMQProperties(groupId, rocketConfig, messageModel);
        Consumer consumer = ONSFactory.createConsumer(properties);
        endpointList.forEach(endpoint -> {

        });

    }

    public void startAllConsumer() {
        synchronized (endpointList) {
            endpointList.forEach(endpoint -> {

            });
        }
    }

    /**
     * 校验consumer端点中的实际方法(@RocketListener所注的方法)
     * <ol>
     *     <li>可见行</li>
     *     <li>方法参数</li>
     *     <li>方法返回值</li>
     * </ol>
     * @param endpoint consumer端点
     * @return         是否通过校验
     */
    private boolean checkRealMethod(RocketListenerEndpoint endpoint) {
        Method method = endpoint.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != RocketConstant.CONSUMER_REAL_METHOD_PARAM_NUMBER) {
            return false;
        }
        return true;
    }

    private String[] resolveTopics(RocketListenerEndpoint endpoint) {
        String[] topics = endpoint.getTopics();
        if(Objects.isNull(topics)){
            return null;
        }
        List<String> result = new ArrayList<>();
        if (topics.length > 0) {
            for (String s : topics) {
                Object topic = resolveExpression(s);
                resolveAsString(topic, result);
            }
        }
        return result.toArray(new String[0]);
    }

    private void resolveAsString(Object resolvedValue, List<String> result) {
        if (resolvedValue instanceof String[]) {
            for (Object object : (String[]) resolvedValue) {
                resolveAsString(object, result);
            }
        }
        if (resolvedValue instanceof String) {
            result.add((String) resolvedValue);
        } else if (resolvedValue instanceof Iterable) {
            for (Object object : (Iterable<Object>) resolvedValue) {
                resolveAsString(object, result);
            }
        } else {
            throw new IllegalArgumentException(String.format("@RocketListener can't resolve '%s' as a String", resolvedValue));
        }
    }

    private Object resolveExpression(String value) {
        String resolvedValue = resolve(value);

        if (!(resolvedValue.startsWith("#{") && value.endsWith("}"))) {
            return resolvedValue;
        }

        return this.resolver.evaluate(resolvedValue, this.expressionContext);
    }

    /**
     * Resolve the specified value if possible.
     * @param value the value to resolve
     * @return the resolved value
     * @see ConfigurableBeanFactory#resolveEmbeddedValue
     */
    private String resolve(String value) {
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }

    public static void main(String[] args) {
        long userId = 15772575071260L;
        System.out.println("test:");
        System.out.println("库:" + (userId / 1000) % 16);
        System.out.println("表:" + (userId / 16000) % 32);

        System.out.println("beta:");
        System.out.println("库:" + (userId / 1000) % 2);
        System.out.println("表:" + (userId / 2000) % 32);

        System.out.println("prod:");
        System.out.println("库:" + (userId / 1000) % 32);
        System.out.println("表:" + (userId / 32000) % 256);
    }

}
