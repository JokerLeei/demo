package com.example.demo.config.redis.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: lijiawei04
 * @date: 2021/1/22 12:42 上午
 */
@Slf4j
@Configuration
public class JedisConfig {

    private final JedisProperties jedisProperties;

    /**
     * 获取redis连接工厂
     *
     * @return RedisConnectionFactory {@link RedisConnectionFactory}
     * {@link JedisConnectionFactory} 此处使用Jedis
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        log.info("JedisConnectionFactory start building... jedisProperties:[{}]", jedisProperties);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(jedisProperties.getHost());
        redisStandaloneConfiguration.setPort(jedisProperties.getPort());
        redisStandaloneConfiguration.setDatabase(jedisProperties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(jedisProperties.getPassword()));

        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder)JedisClientConfiguration.builder();
        builder.usePooling();
        builder.poolConfig(jedisPoolConfig);
        builder.connectTimeout(Duration.ofMillis(jedisProperties.getTimeout()));
        builder.readTimeout(Duration.ofMillis(jedisProperties.getTimeout()));

        return new JedisConnectionFactory(redisStandaloneConfiguration, builder.build());
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        log.info("JedisPoolConfig start building...");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(jedisProperties.getMinIdle());
        jedisPoolConfig.setMaxTotal(jedisProperties.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        return jedisPoolConfig;
    }

    @Autowired
    public JedisConfig(JedisProperties jedisProperties) {
        this.jedisProperties = jedisProperties;
    }

}
