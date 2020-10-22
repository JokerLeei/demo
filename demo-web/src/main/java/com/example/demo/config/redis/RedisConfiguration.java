package com.example.demo.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis configuration
 *
 * @author: lijiawei04
 * @date: 2020/10/12 11:20 上午
 */
@Slf4j
@Configuration
public class RedisConfiguration {

    @Configuration
    protected static class JedisConfiguration {

        private final RedisProperties.JedisProperties jedisProperties;

        /**
         * 获取redis连接工厂
         *
         * @return RedisConnectionFactory {@link RedisConnectionFactory}
         * {@link JedisConnectionFactory} 此处使用Jedis
         * {@link LettuceConnectionFactory}
         * {@link RedissonConnectionFactory}
         */
        @Bean
        public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
            log.info("[INIT] JedisConnectionFactory start building... jedisProperties:[{}]", jedisProperties);
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
            log.info("[INIT] JedisPoolConfig start building...");
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(jedisProperties.getMaxIdle());
            jedisPoolConfig.setMinIdle(jedisProperties.getMinIdle());
            jedisPoolConfig.setMaxTotal(jedisProperties.getMaxTotal());
            jedisPoolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
            return jedisPoolConfig;
        }

        @Autowired
        public JedisConfiguration(RedisProperties.JedisProperties jedisProperties) {
            this.jedisProperties = jedisProperties;
        }

    }

    @Configuration
    protected static class RedissonConfiguration {

        private final RedisProperties.RedissonProperties redissonProperties;

        /**
         * 获取redis连接工厂
         *
         * @return RedisConnectionFactory {@link RedisConnectionFactory}
         * {@link JedisConnectionFactory}
         * {@link LettuceConnectionFactory}
         * {@link RedissonConnectionFactory} 此处使用Redisson
         */
        @Bean
        public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
            log.info("[INIT] RedissonConnectionFactory start building...");
            return new RedissonConnectionFactory(redissonClient);
        }

        @Bean
        public RedissonClient redissonClientSingle() {
            log.info("[INIT] RedissonClient start building...");
            Config config = new Config();
            config.useSingleServer()
                    .setAddress(redissonProperties.getAddress())
                    .setPassword(redissonProperties.getPassword())
                    .setDatabase(redissonProperties.getDatabase())
                    .setKeepAlive(redissonProperties.isKeepAlive())
                    .setConnectionPoolSize(redissonProperties.getConnectPoolSize())
                    .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
                    .setConnectTimeout(redissonProperties.getConnectTimeout())
                    .setRetryAttempts(redissonProperties.getRetryAttempts());
            config.setThreads(redissonProperties.getThread());
            config.setEventLoopGroup(new NioEventLoopGroup());
            return Redisson.create(config);
        }

        @Autowired
        public RedissonConfiguration(RedisProperties.RedissonProperties redissonProperties) {
            this.redissonProperties = redissonProperties;
        }

    }

    @Configuration
    protected static class RedisTemplateConfiguration {

        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public RedisTemplate<Object, Object> redisTemplate() {
            log.info("[INIT] RedisTemplate start building...");
            RedisTemplate<Object, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }

        @Bean
        public StringRedisTemplate stringRedisTemplate() {
            log.info("[INIT] StringRedisTemplate start building...");
            StringRedisTemplate template = new StringRedisTemplate();
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }

        @Autowired
        public RedisTemplateConfiguration(@Qualifier("jedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
            this.redisConnectionFactory = redisConnectionFactory;
        }

        public RedisTemplateConfiguration(ObjectProvider<RedisConnectionFactory> redisConnectionFactoryObjectProvider) {
            if (redisConnectionFactoryObjectProvider.orderedStream().findFirst().isPresent()) {
                this.redisConnectionFactory = redisConnectionFactoryObjectProvider.orderedStream().findFirst().get();
            }
        }

    }

}
