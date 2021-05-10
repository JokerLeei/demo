package com.demo.kafka.producer;

import com.alibaba.fastjson.JSONObject;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/29 5:45 下午
 */
@Slf4j
@RestController
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送简单消息，并添加成功失败回调
     */
    @GetMapping("/kafka/normal/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("topic1", normalMessage);
        future.addCallback(
                result -> log.info("[简单发送] 成功！send result:[{}]", JSONObject.toJSONString(result)),
                failure -> log.error("[简单发送] 失败！send result:[{}]", JSONObject.toJSONString(failure)));
    }
}