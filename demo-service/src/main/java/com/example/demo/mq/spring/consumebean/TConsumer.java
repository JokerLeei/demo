package com.example.demo.mq.spring.consumebean;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/1/20 4:03 下午
 */
@Slf4j
//@Component
public class TConsumer extends AbstractConsumer {

    public TConsumer() {
        super("GID_demo", "test-topic");
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            String body = new String(message.getBody());
            log.info("consume message... body is:[{}]", body);
            return Action.CommitMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ReconsumeLater;
        }
    }
}
