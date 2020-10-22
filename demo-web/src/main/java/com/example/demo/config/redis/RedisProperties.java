package com.example.demo.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Configuration properties for Redis.
 *
 * @author: lijiawei04
 * @date: 2020/10/12 3:25 下午
 */
@Data
@Component
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class RedisProperties {

    private final JedisProperties jedisProperties;
    private final LettuceProperties lettuceProperties;
    private final RedissonProperties redissonProperties;

    @Getter
    @ToString
    @Component
    @PropertySource("classpath:redis.properties")
    public static class JedisProperties {
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

    @Getter
    @ToString
    @Component
    public static class LettuceProperties {
    }

    @Getter
    @ToString
    @Component
    @PropertySource("classpath:redisson-config.properties")
    public static class RedissonProperties {
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

}
