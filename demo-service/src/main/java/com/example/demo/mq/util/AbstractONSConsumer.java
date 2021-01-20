package com.example.demo.mq.util;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;
import java.util.Properties;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * ONS consumer 抽象模版方法
 *
 * @author: lijiawei04
 * @date: 2021/1/20 10:13 上午
 */
@Slf4j
public abstract class AbstractONSConsumer implements MessageListener {

    @Value("${accessKey:}")
    private String accessKey;
    @Value("${secretKey:}")
    private String secretKey;
    @Value("${nameSrvAddr:}")
    private String nameSrvAddr;

    private volatile Consumer consumer;

    private final String topic;

    private final String tags;

    private final String group;

    public AbstractONSConsumer(String topic, String group) {
        this(topic, "*", group);
    }

    public AbstractONSConsumer(String topic, String tags, String group) {
        this.topic = topic;
        this.tags = tags;
        this.group = group;
    }

    @PostConstruct
    public void init(){
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

    /**
     * 消费Message, 根据消费结果判断该条消息消费成功or稍后重新消费
     *
     * @param message {@link Message}
     * @param context {@link ConsumeContext}
     * @return        {@link Action}
     */
    @Override
    public Action consume(Message message, ConsumeContext context) {
        if (Objects.isNull(message)) {
            log.warn("consume empty message! topic:[{}], tags:[{}], group:[{}]", topic, tags, group);
            return Action.CommitMessage;
        }
        try {
            // consume message
            ConsumeResult<?> consumeResult = consumeMessage(message);

            // do something after consume message
            afterConsume(consumeResult);

            if (ConsumeResult.ConsumeResultStatus.FAIL_NOT_RETRY == consumeResult.getStatus()) {
                log.error("consume message fail need retry! topic:[{}], tags:[{}], group:[{}], msg:[{}]", topic, tags, group, new String(message.getBody()));
                return Action.ReconsumeLater;
            }
            return Action.CommitMessage;
        } catch (Exception e) {
            log.error("consume message fail! topic:[{}], tags:[{}], group:[{}], msg:[{}]", topic, tags, group, new String(message.getBody()), e);
            return Action.ReconsumeLater;
        }
    }

    /**
     * 消费消息接口，由应用来实现<br>
     * 网络抖动等不稳定的情形可能会带来消息重复, 对重复消息敏感的业务可对消息做幂等处理
     *
     * @param message 消息 {@link Message}
     * @return        消费结果, 如果应用抛出异常或者返回Null等价于返回Action.ReconsumeLater
     * @see           <a href="https://help.aliyun.com/document_detail/44397.html">如何做到消费幂等</a>
     */
    public abstract ConsumeResult<?> consumeMessage(Message message);

    /**
     * do something
     * <ol>
     *     <li>save DB</li>
     *     <li>fail and retry</li>
     *     <li>alert</li>
     * </ol>
     */
    public void afterConsume(ConsumeResult<?> consumeResult) {
    }

}
