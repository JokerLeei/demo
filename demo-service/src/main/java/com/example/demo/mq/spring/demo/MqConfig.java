package com.example.demo.mq.spring.demo;

import com.aliyun.openservices.ons.api.PropertyKeyConst;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/1/20 12:15 下午
 */
@Data
//@Configuration
//@ConfigurationProperties(prefix = "rocketmq")
public class MqConfig {

    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String topic;
    private String groupId;
    private String tag;
//    private String orderTopic;
//    private String orderGroupId;
//    private String orderTag;

    public Properties getMqProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }

}
