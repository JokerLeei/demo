package com.demo.rocketmq.annotation.config;


import javax.sql.DataSource;

import lombok.Data;

/**
 * 客户端需要在spring容器中注册此bean并提供配置数据
 *
 * @author: lijiawei04
 * @date: 2021/3/15 7:54 下午
 */
@Data
public class RocketConfig {

    /**
     * NameServer 地址
     */
    private String namesrvAddr;

    /**
     * 阿里云 accessKey
     */
    private String accessKey;

    /**
     * 阿里云 secretKey
     */
    private String secretKey;

    /**
     * rocketMQ default groupId
     * 当没有指定消费者group时，使用此值
     */
    private String defaultGroupId;

    /**
     *
     */
//    private TransactionTemplate transactionTemplate;

    /**
     *
     */
//    private DataSource dataSource;

}
