//package com.example.demo.config.redis.redisson;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.spring.data.connection.RedissonConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//
//import io.netty.channel.nio.NioEventLoopGroup;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author: lijiawei04
// * @date: 2021/1/22 1:01 上午
// */
//@Slf4j
//@Configuration
//public class RedissonConfiguration {
//
//    private final RedissonProperties redissonProperties;
//
//    /**
//     * 获取redis连接工厂
//     *
//     * @return RedisConnectionFactory {@link RedisConnectionFactory}
//     * {@link RedissonConnectionFactory} 此处使用Redisson
//     */
//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
//        return new RedissonConnectionFactory(redissonClient);
//    }
//
//    @Bean
//    public RedissonClient redissonClientSingle() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress(redissonProperties.getAddress())
//                .setPassword(redissonProperties.getPassword())
//                .setDatabase(redissonProperties.getDatabase())
//                .setKeepAlive(redissonProperties.isKeepAlive())
//                .setConnectionPoolSize(redissonProperties.getConnectPoolSize())
//                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
//                .setConnectTimeout(redissonProperties.getConnectTimeout())
//                .setRetryAttempts(redissonProperties.getRetryAttempts());
//        config.setThreads(redissonProperties.getThread());
//        config.setEventLoopGroup(new NioEventLoopGroup());
//        return Redisson.create(config);
//    }
//
//    @Autowired
//    public RedissonConfiguration(RedissonProperties redissonProperties) {
//        this.redissonProperties = redissonProperties;
//    }
//
//}
