package com.auto.rocketmq.annotation.util;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.auto.rocketmq.annotation.config.RocketConfig;

import java.util.Properties;

/**
 * @author: lijiawei04
 * @date: 2021/3/16 11:26 上午
 */
public class RocketPropertiesUtil {

    private static final Object lock = new Object();

    public static Properties getMQProperties(String groupId, RocketConfig config, String messageModel){
        Properties properties = getMQProperties(groupId, config);
        // 默认为集群模式订阅
        properties.put(PropertyKeyConst.MessageModel, messageModel);
        return properties;
    }

    public static Properties getMQProperties(String groupId, RocketConfig config){
        Properties properties = new Properties();
        // 您在控制台创建的 Group_ID
        properties.put(PropertyKeyConst.GROUP_ID, groupId);
        synchronized (lock){
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, config.getAccessKey());
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, config.getSecretKey());
        }
        // 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, config.getNamesrvAddr());
        return properties;
    }

}
