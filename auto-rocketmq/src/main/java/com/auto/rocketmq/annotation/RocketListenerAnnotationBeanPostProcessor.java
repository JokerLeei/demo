package com.auto.rocketmq.annotation;

import com.auto.rocketmq.annotation.config.RocketConfig;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * spring容器启动时处理被{@link RocketListener}注解标注的方法，将注解中的数据转为Consumer并启动
 *
 * @author: lijiawei04
 * @date: 2021/3/15 3:19 下午
 */
@Slf4j
public class RocketListenerAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    /**
     * localCache 提高性能, 防止重复判断
     */
    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    @Resource
    private RocketConfig rocketConfig;

    /**
     * RocketMQ consumer/endpoint 注册管理器
     */
    private final RocketRegistrar rocketRegistrar = new RocketRegistrar();

    private BeanFactory beanFactory;

    /**
     * 用于解析spring EL表达式
     */
    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();
    private BeanExpressionContext expressionContext;

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public void afterSingletonsInstantiated() {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!nonAnnotatedClasses.contains(bean.getClass())) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Map<Method, Set<RocketListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass, new MethodIntrospector.MetadataLookup<Set<RocketListener>>() {
                @Override
                public Set<RocketListener> inspect(Method method) {
                    Set<RocketListener> listenerMethods = findListenerAnnotations(method);
                    return (!listenerMethods.isEmpty() ? listenerMethods : null);
                }
            });
            if (CollectionUtils.isEmpty(annotatedMethods)) {
                nonAnnotatedClasses.add(bean.getClass());
            } else {
                // 含有@RocketListener注解的方法
                annotatedMethods.forEach(((method, listeners) -> listeners.forEach((listener) -> processRocketListener(listener, method, bean, beanName))));
            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
            this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
        }
    }

    /**
     * 查找 method 是否有 RocketListener 注解
     * @param method method
     * @return null / 注解
     */
    private Set<RocketListener> findListenerAnnotations(Method method){
        Set<RocketListener> listeners = new HashSet<>();
        RocketListener annotation = AnnotationUtils.findAnnotation(method, RocketListener.class);
        if(annotation != null){
            listeners.add(annotation);
        }
        /*
         * AnnotationUtils.getRepeatableAnnotations does not look at interfaces
         */
        RocketListeners annotations = AnnotationUtils.findAnnotation(method, RocketListeners.class);
        if (annotations != null) {
            listeners.addAll(Arrays.asList(annotations.value()));
        }
        return listeners;
    }

    /**
     * 注册一个{@link RocketListenerEndpoint}
     *
     * @param listener {@link RocketListener}
     * @param method   实际调用的方法
     * @param bean     spring bean
     * @param beanName spring beanName
     */
    private void processRocketListener(RocketListener listener, Method method, Object bean, String beanName) {
        RocketListenerEndpoint endpoint = new RocketListenerEndpoint();
        endpoint.setTopics(resolveTopics(listener));
        endpoint.setGroupId((String) resolveExpression(listener.groupId()));
        endpoint.setTag((String) resolveExpression(listener.tag()));
        endpoint.setRealMethod(method);
        endpoint.setMessageModel(listener.messageModel());
        endpoint.setBean(bean);
        endpoint.setBeanName(beanName);
        rocketRegistrar.registerEndpoint(endpoint);
    }

    private String[] resolveTopics(RocketListener listener) {
        String[] topics = listener.topics();
        List<String> result = new ArrayList<>();
        if (topics.length > 0) {
            for (String s : topics) {
                Object topic = resolveExpression(s);
                resolveAsString(topic, result);
            }
        }
        return result.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    private void resolveAsString(Object resolvedValue, List<String> result) {
        if (resolvedValue instanceof String[]) {
            for (Object object : (String[]) resolvedValue) {
                resolveAsString(object, result);
            }
        }
        if (resolvedValue instanceof String) {
            result.add((String) resolvedValue);
        }
        else if (resolvedValue instanceof Iterable) {
            for (Object object : (Iterable<Object>) resolvedValue) {
                resolveAsString(object, result);
            }
        }
        else {
            throw new IllegalArgumentException(String.format(
                    "@RocketListener can't resolve '%s' as a String", resolvedValue));
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

}
