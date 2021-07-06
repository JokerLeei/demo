package com.demo.rocketmq.annotation;

import com.aliyun.openservices.ons.api.PropertyValueConst;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * RocketMQ Listener endpoint
 *
 * @author: lijiawei04
 * @date: 2021/3/15 5:48 下午
 */
@Data
public class RocketListenerEndpoint {

    /**
     * topic(s)
     */
    private String[] topics;

    /**
     * groupId
     */
    private String groupId;

    /**
     * tag
     */
    private String tag;

    /**
     * 消费模式
     * {@link PropertyValueConst}
     */
    private String messageModel;

    /**
     * 实际调用的方法
     */
    private Method realMethod;

    /**
     * spring bean
     */
    private Object bean;

    /**
     * spring beanName
     */
    private String beanName;

}
