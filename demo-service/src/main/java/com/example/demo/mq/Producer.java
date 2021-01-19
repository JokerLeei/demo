package com.example.demo.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试 Rocket MQ Producer
 *
 * @author: lijiawei04
 * @date: 2021/1/18 2:30 下午
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws Exception {
        // 创建一个消息生产者, 并设置一个消息生产者组
        DefaultMQProducer producer = new DefaultMQProducer("producer_test");

        // 指定 NameServer 的地址
        producer.setNamesrvAddr("localhost:9876");

        // 初始化producer, 整个应用生命周期内只需要初始化一次
        producer.start();

        try {
            Message msg = new Message("ttt" /* 消息主题名 */,
                                      "TagA" /* 消息标签 */,
                                      ("Hello Java demo RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息内容 */);

            // 发送消息并返回结果
            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 一旦生产者实例不再被使用则将其关闭，包括清理资源，关闭网络连接等
        producer.shutdown();
    }

}
