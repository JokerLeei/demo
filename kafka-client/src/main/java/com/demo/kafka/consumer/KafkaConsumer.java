package com.demo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/29 5:46 下午
 */
@Slf4j
@Component
public class KafkaConsumer {

    // 消费监听
    @KafkaListener(topics = { "topic1" })
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("[简单消费] topic:[{}], partition:[{}], msg:[{}]", record.topic(), record.partition(), record.value());
    }
}