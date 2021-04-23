package com.auto.rocketmq.annotation;

import com.aliyun.openservices.ons.api.PropertyValueConst;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RocketMq listener(consumer)
 *
 * @author: lijiawei04
 * @date: 2021/3/12 6:05 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RocketListener {

    /**
     * topic消息主题(可以多个)
     *
     * @return topic
     */
    String[] topics() default {};

    /**
     * 一个 Group ID 代表一个 Consumer 实例群组。
     * 同一个消费者 Group ID 下所有的 Consumer 实例必须保证订阅的 Topic 一致，并且也必须保证订阅 Topic 时设置的过滤规则（Tag）一致。
     * 否则消息可能会丢失。
     *
     * @return groupId
     */
    String groupId() default "";

    /**
     * rocketMQ tag
     *
     * @return tag
     */
    String tag() default "*";

    /**
     * 消费模式(集群模式/广播模式)
     *
     * @return {@link PropertyValueConst}
     */
    String messageModel() default PropertyValueConst.CLUSTERING;

}
