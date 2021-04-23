package com.auto.rocketmq.annotation.config;

import org.springframework.transaction.support.TransactionTemplate;

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
     * rocketMQ groupId
     */
    private String groupId;

    /**
     *
     */
    private TransactionTemplate transactionTemplate;

    /**
     *
     */
    private DataSource dataSource;

}
