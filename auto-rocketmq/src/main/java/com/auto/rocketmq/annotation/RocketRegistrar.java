package com.auto.rocketmq.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.aliyun.openservices.shade.com.google.common.collect.Lists;
import com.auto.rocketmq.annotation.config.RocketConfig;
import com.auto.rocketmq.annotation.constant.RocketConstant;
import com.auto.rocketmq.annotation.util.RocketPropertiesUtil;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * RocketMQ consumer/endpoint 注册管理器
 *
 * @author: lijiawei04
 * @date: 2021/3/15 5:27 下午
 */
@Slf4j
public class RocketRegistrar {

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

    public void startAllConsumer() {
        synchronized (endpointList) {
            List<RocketListenerEndpoint> defaultGroupEndPointList = new ArrayList<>();
            endpointList.stream()
                    .filter(e -> {
                        // 真实方法有效性校验
                        if (!checkRealMethod(e)) {
                           log.warn("method:[{}] is illegal, jump consumer", e.getRealMethod());
                           return false;
                        }
                        // 没有 groupId 的使用 RocketConfig 中配置的 defaultGroupId
                        if (StringUtils.isEmpty(e.getGroupId())) {
                            log.info("method:[{}] groupId is null, set it to defaultGroup", e.getRealMethod());
                            defaultGroupEndPointList.add(e);
                        }
                        return true; })
                    .collect(Collectors.groupingBy(RocketListenerEndpoint::getGroupId))
                    .forEach((groupId, eps) -> {
                        if (CollectionUtils.isEmpty(eps)) {
                            log.warn("groupId:[{}] consumer is empty", groupId);
                            return;
                        }
                        if (!checkMessageModel(eps)) {
                            log.error("同一group id订阅关系需要保持一致, groupId = {}", groupId);
                            return;
                        }
                        String messageModel = eps.stream().map(RocketListenerEndpoint::getMessageModel).findFirst().orElse(PropertyValueConst.CLUSTERING);
                        // 注册同一个 groupId 下的所有 consumer
                        registerConsumer(groupId, messageModel, eps);
                    });
            // 注册默认 groupId 下的所有 consumer
            registerConsumer(rocketConfig.getDefaultGroupId(), PropertyValueConst.CLUSTERING, defaultGroupEndPointList);
        }
    }

    public void registerConsumer(String groupId, String messageModel, List<RocketListenerEndpoint> endpointList) {
        Properties properties = RocketPropertiesUtil.getMQProperties(groupId, rocketConfig, messageModel);
        Consumer consumer = ONSFactory.createConsumer(properties);
        endpointList.forEach(endpoint -> {
            String[] topics = endpoint.getTopics();
            String tag = endpoint.getTag();
            for(String topic : topics){
                consumer.subscribe(topic, tag, new MessageListener() {
                    @Override
                    public Action consume(Message message, ConsumeContext consumeContext) {
                        try {
                            return doInvoke(endpoint, message, consumeContext);
                        } catch (Exception e) {
                            log.warn("method consumer message failed, method = {}, msg = {}",
                                     endpoint.getRealMethod().getName(), JSON.toJSONString(message, SerializerFeature.IgnoreNonFieldGetter), e);
                            return Action.ReconsumeLater;
                        }
                    }
                });
            }
        });
        consumer.start();
        consumerList.add(consumer);
    }

    /**
     * 反射调用实际方法
     * @param endpoint   consumer endpoint
     * @param message    message
     * @param context    context
     * @return           {@link Action}
     * @throws Exception e
     */
    private Action doInvoke(RocketListenerEndpoint endpoint, Message message, ConsumeContext context) throws Exception {
        Method realMethod = endpoint.getRealMethod();
        // 方法可见性设置为public
        ReflectionUtils.makeAccessible(realMethod);
        try {
            Object result = realMethod.invoke(endpoint.getBean(), message, context);
            if (result instanceof Boolean){
                Boolean booleanResult = (Boolean)result;
                if (booleanResult) {
                    return Action.CommitMessage;
                }else{
                    return Action.ReconsumeLater;
                }
            }
            if (result instanceof Action) {
                return (Action) result;
            }
            return Action.CommitMessage;
        } catch (InvocationTargetException ex) {
            Throwable targetException = ex.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
            }
            else if (targetException instanceof Error) {
                throw (Error) targetException;
            }
            else if (targetException instanceof Exception) {
                throw (Exception) targetException;
            }
            else {
                throw new IllegalStateException(targetException);
            }
        }
    }

    /**
     * 校验每个consumer端点中的实际方法
     * <ol>
     *     <li>方法参数</li>
     *     <li>方法返回值</li>
     * </ol>
     * @param endpoint consumer端点
     * @return         是否通过校验
     */
    private boolean checkRealMethod(RocketListenerEndpoint endpoint) {
        Method method = endpoint.getRealMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();
        if (parameterTypes.length != RocketConstant.CONSUMER_REAL_METHOD_PARAM_NUMBER) {
            log.warn("beanName param length is illegal, expect is two");
            return false;
        }
        if(returnType != boolean.class && returnType != Action.class && returnType != Void.class){
            log.warn("beanName returnType is illegal, expect is void or boolean or Action");
            return false;
        }
        return true;
    }

    /**
     * 校验同一个group id下的所有consumer端点订阅关系是否一致
     * @param endpoints consumer端点s
     * @return          是否通过校验
     */
    private boolean checkMessageModel(List<RocketListenerEndpoint> endpoints){
        return endpoints.stream().map(RocketListenerEndpoint::getMessageModel).collect(Collectors.toSet()).size() == 1;
    }

}
