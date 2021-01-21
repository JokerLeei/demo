package com.example.demo.mq.spring.consumer;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

/**
 * ONS consumer 抽象模版方法
 *
 * @author: lijiawei04
 * @date: 2021/1/20 10:13 上午
 */
@Slf4j
public abstract class AbstractONSConsumer implements MessageListener {

    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${nameSrvAddr}")
    private String nameSrvAddr;

    private final String group;
    private final String topic;
    private final String tags;

    private volatile Consumer consumer;

    public AbstractONSConsumer(String group, String topic) {
        this(group, topic, "*");
    }

    public AbstractONSConsumer(String group, String topic, String tags) {
        this.group = group;
        this.topic = topic;
        this.tags = tags;
    }

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, group);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, nameSrvAddr);
        consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(topic, tags, this);
        consumer.start();

        log.info("abstract ons consumer init property success! topic:[{}], tags:[{}], group:[{}]", topic, tags, group);
    }

    @PreDestroy
    public void destroy() {
        consumer.shutdown();

        log.info("shutdown consumer...");
    }

}
