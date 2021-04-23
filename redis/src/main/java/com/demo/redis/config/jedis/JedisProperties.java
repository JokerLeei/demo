package com.demo.redis.config.jedis;

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
@PropertySource("classpath:redis.properties")
public class JedisProperties {

    @Value("${redis.hostName:localhost}")           private String host;
    @Value("${redis.port:6379}")                    private int port;
    @Value("${redis.password:123456}")              private String password;
    @Value("${redis.database:1}")                   private int database;
    @Value("${redis.timeout:10000}")                private int timeout;
    @Value("${redis.testOnBorrow:true}")            private boolean testOnBorrow;
    @Value("${redis.maxIdle:8}")                    private int maxIdle;
    @Value("${redis.minIdle:0}")                    private int minIdle;
    @Value("${redis.maxTotal:8}")                   private int maxTotal;
    @Value("${redis.maxWaitMillis:-1}")             private int maxWaitMillis;

}
