package com.example.demo.config.redis.template;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/1/22 12:55 上午
 */
@Slf4j
@Configuration
public class RedisTemplateConfig {

    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Autowired
    public RedisTemplateConfig(@Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public RedisTemplateConfig(ObjectProvider<RedisConnectionFactory> redisConnectionFactoryObjectProvider) {
        if (redisConnectionFactoryObjectProvider.orderedStream().findFirst().isPresent()) {
            this.redisConnectionFactory = redisConnectionFactoryObjectProvider.orderedStream().findFirst().get();
        }
    }

}
