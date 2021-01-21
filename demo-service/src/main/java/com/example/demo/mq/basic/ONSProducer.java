package com.example.demo.mq.basic;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试 ONS Producer
 *
 * @author: lijiawei04
 * @date: 2021/1/18 2:30 下午
 */
@Slf4j
public class ONSProducer {

    /**
     * <ol>
     *     <li>AccessKey</li>
     *     <li>SecretKey</li>
     *     <li>NAMESRV_ADDR</li>
     * </ol>
     */
    public static void main(String[] args) {
        Properties properties = new Properties();
        // AccessKey ID阿里云身份验证，在阿里云服务器管理控制台创建。
        properties.put(PropertyKeyConst.AccessKey, "LTAI4G5CTrSBZPX2nKP8q36p");
        // AccessKey Secret阿里云身份验证，在阿里云服务器管理控制台创建。
        properties.put(PropertyKeyConst.SecretKey, "VJGM96sZsXLIvnRZag6ZixQVPKGZ8f");
        // 设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置TCP协议接入点，进入控制台的实例详情页面的TCP协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, "http://MQ_INST_1048400171395301_BXcXru8T.mq-internet-access.mq-internet.aliyuncs.com:80");

        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();

        Message msg = new Message(
                // Message所属的Topic。
                "test-topic",
                // Message Tag，可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在消息队列RocketMQ版的服务器过滤。
                "TagA",
                // Message Body，任何二进制形式的数据，消息队列RocketMQ版不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式。
                "Hello MQ".getBytes());

        // 设置代表消息的业务关键属性，请尽可能全局唯一。以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发。
        msg.setKey("ORDERID_100");

        // 异步发送消息，发送结果通过callback返回给客户端。
        producer.sendAsync(msg, new SendCallback() {
            @Override
            public void onSuccess(final SendResult sendResult) {
                // 消息发送成功。
                log.info("send message success. topic:[{}], msgId:[{}]", sendResult.getTopic(), sendResult.getMessageId());
            }

            @Override
            public void onException(OnExceptionContext context) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
                log.error("send message failed. topic:[{}], msgId:[{}]", context.getTopic(), context.getMessageId());
            }
        });

        // 在callback返回之前即可取得msgId。
        log.info("send message async. topic:[{}], msgId:[{}]", msg.getTopic(), msg.getMsgID());

        // 在应用退出前，销毁Producer对象。 注意：如果不销毁也没有问题。
        producer.shutdown();
    }

}
