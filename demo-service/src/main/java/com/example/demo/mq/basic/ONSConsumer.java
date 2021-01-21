package com.example.demo.mq.basic;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试 ONS Consumer
 *
 * @author: lijiawei04
 * @date: 2021/1/19 11:44 上午
 */
@Slf4j
public class ONSConsumer {

    /**
     * <ol>
     *     <li>GROUP_ID</li>
     *     <li>AccessKey</li>
     *     <li>SecretKey</li>
     *     <li>NAMESRV_ADDR</li>
     * </ol>
     */
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 您在控制台创建的Group ID。
        properties.put(PropertyKeyConst.GROUP_ID, "GID_demo");
        // AccessKey ID阿里云身份验证，在阿里云服务器管理控制台创建。
        properties.put(PropertyKeyConst.AccessKey, "LTAI4G5CTrSBZPX2nKP8q36p");
        // AccessKey Secret阿里云身份验证，在阿里云服务器管理控制台创建。
        properties.put(PropertyKeyConst.SecretKey, "VJGM96sZsXLIvnRZag6ZixQVPKGZ8f");
        // 设置TCP协议接入点，进入控制台的实例详情页面的TCP协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, "http://MQ_INST_1048400171395301_BXcXru8T.mq-internet-access.mq-internet.aliyuncs.com:80");
        // 集群订阅方式（默认）。
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式。
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("test-topic", "TagA||TagB", new MessageListener() { // 订阅多个Tag
            public Action consume(Message message, ConsumeContext context) {
                log.info("Receive:[{}]", message);
                return Action.CommitMessage;
            }
        });

        // 订阅另外一个Topic，如需取消订阅该Topic，请删除该部分的订阅代码，重新启动消费端即可。
//        consumer.subscribe("test-topic-Other", "*", new MessageListener() { // 订阅全部Tag。
//            public Action consume(Message message, ConsumeContext context) {
//                log.info("Receive:[{}]", message);
//                return Action.CommitMessage;
//            }
//        });

        consumer.start();
        log.info("Consumer Started");
    }
}
