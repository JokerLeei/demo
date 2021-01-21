package com.example.demo.mq.spring.consumebean;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * ONS 基于 spring 的 consumer 抽象类
 *
 * @author: lijiawei04
 * @date: 2021/1/20 2:06 下午
 */
@Slf4j
public abstract class AbstractConsumer extends ConsumerBean implements MessageListener {

    /**
     * 将消费者线程数固定为20个 20为默认值
     */
    private static final int CONSUME_THREAD_NUMS = 20;

    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${nameSrvAddr}")
    private String nameSrvAddr;

    private final String groupId;
    private final String topic;
    private final String tags;

    public AbstractConsumer(String groupId, String topic) {
        this(groupId, topic, "*");
    }

    public AbstractConsumer(String groupId, String topic, String tags) {
        this.groupId = groupId;
        this.topic = topic;
        this.tags = tags;
    }

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        // 配置文件
        properties.setProperty(PropertyKeyConst.GROUP_ID, groupId);
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, String.valueOf(CONSUME_THREAD_NUMS));
        properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, nameSrvAddr);
        this.setProperties(properties);

        // 订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression(tags);
        subscriptionTable.put(subscription, this);
        this.setSubscriptionTable(subscriptionTable);

        this.start();
        log.info("init Consumer success! group is[{}], topic is:[{}], tag is:[{}], properties is:[{}]", groupId, topic, tags, properties);
    }

}
