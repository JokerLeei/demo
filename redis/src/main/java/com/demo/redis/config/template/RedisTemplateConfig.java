package com.demo.redis.config.template;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    @Primary
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer  = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(customObjectMapper());

        // string类型的序列化
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);

        // hash类型的序列化
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        return template;
    }

    // 增加redis相关配置 防止在进行序列化时候出现异常 已解决test以及本地不复现 beta上必现序列化问题
    // 参考网址https://github.com/spring-projects/spring-session/issues/387
    private ObjectMapper customObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(
                mapper.getVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
//        // register the MixIn
//        mapper.addMixIn(AssertionImpl.class, AssertionMixin.class);
//        // register the MixIn
//        mapper.addMixIn(AttributePrincipalImpl.class, AttributePrincipalMixin.class);
//        // enable default typing
//        mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class");
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
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
