package com.example.demo.mq.spring.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/1/20 7:59 下午
 */
@Slf4j
//@Component
public class TONSConsumer extends AbstractONSConsumer {

    public TONSConsumer() {
        super("GID_demo", "test-topic");
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
        log.info("Receive:[{}]", message);
        try {
            // do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            // 消费失败
            return Action.ReconsumeLater;
        }
    }
}
