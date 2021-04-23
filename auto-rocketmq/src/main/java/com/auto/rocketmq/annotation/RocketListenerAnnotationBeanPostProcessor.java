package com.auto.rocketmq.annotation;

import com.auto.rocketmq.annotation.config.RocketConfig;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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
            Map<Method, Set<RocketListener>> annotatedMethods =
                    MethodIntrospector.selectMethods(targetClass, (MethodIntrospector.MetadataLookup<Set<RocketListener>>) method -> {
                        Set<RocketListener> listenerMethods = findListenerAnnotations(method);
                        return (!listenerMethods.isEmpty() ? listenerMethods : null);
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
    }

    /**
     * 查找 method 是否有 RocketListener 注解
     * @param method method
     * @return null / 1个 / 多个
     */
    private Set<RocketListener> findListenerAnnotations(Method method){
        Set<RocketListener> listeners = new HashSet<>();
        RocketListener listener = AnnotationUtils.findAnnotation(method, RocketListener.class);
        if(Objects.nonNull(listener)){
            listeners.add(listener);
        }
        listeners.add(listener);
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
        endpoint.setTopics(listener.topics());
        endpoint.setGroupId(listener.groupId());
        endpoint.setTag(listener.tag());
        endpoint.setMethod(method);
        endpoint.setMessageModel(listener.messageModel());
        endpoint.setBean(bean);
        endpoint.setBeanName(beanName);
        rocketRegistrar.registerEndpoint(endpoint);
    }

}
