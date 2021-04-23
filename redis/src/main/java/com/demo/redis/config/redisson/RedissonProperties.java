package com.demo.redis.config.redisson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/1/22 12:26 上午
 */
@Data
@Configuration
@PropertySource("classpath:redisson-config.properties")
public class RedissonProperties {

    @Value("${spring.redisson.address:localhost:6379}")         private String address;
    @Value("${spring.redisson.database:1}")                     private int database;
    @Value("${spring.redisson.password:123456}")                private String password;
    @Value("${spring.redisson.connectTimeout:10000}")           private int connectTimeout;
    @Value("${spring.redisson.connectionPoolSize:64}")          private int connectPoolSize;
    @Value("${spring.redisson.connectionMinimumIdleSize:10}")   private int connectionMinimumIdleSize;
    @Value("${spring.redisson.thread:2}")                       private int thread;
    @Value("${spring.redisson.retryAttempts:3}")                private int retryAttempts;
    @Value("${spring.redisson.keepAlive:true}")                 private boolean keepAlive;

}
