package com.demo.redis.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import javax.annotation.Resource;

/**
 * @author: lijiawei04
 * @date: 2021/5/26 2:13 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    private static final String KEY_STRING = "test:str";

    private static final String KEY_SET = "test:set";

    private static final String KEY_ZSET = "test:zset";

    private static final String KEY_LIST = "test:list";

    private static final String KEY_HASH = "test:hash";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * str.get()
     */
    @Test
    public void testStringGet() {
        System.out.println(Optional.ofNullable(redisTemplate.opsForValue().get(KEY_STRING)).orElse(0));
    }

    /**
     * str.increment()
     */
    @Test
    public void testStringInc() {
        System.out.println(redisTemplate.opsForValue().increment(KEY_STRING, 1));
    }

    /**
     * set.add()
     */
    @Test
    public void testSetAdd() {
        Long var = 12345678900000L;
        redisTemplate.opsForSet().add(KEY_SET, var);
    }

    @Test
    public void testZSetAdd() {
        String v1 = "apple";
        String v2 = "banana";
        String v3 = "orange";
        String v4 = "melon";
        redisTemplate.opsForZSet().add(KEY_ZSET, v1, 5);
        redisTemplate.opsForZSet().add(KEY_ZSET, v2, 8);
        redisTemplate.opsForZSet().add(KEY_ZSET, v3, 3);
        redisTemplate.opsForZSet().add(KEY_ZSET, v4, 3);
    }

}
