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

    private static final String KEY1 = "test:str";

    private static final String KEY2 = "test:set";

    private static final String KEY3 = "test:zset";

    private static final String KEY4 = "test:list";

    private static final String KEY5 = "test:hash";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * string
     * set
     */
    @Test
    public void test01() {
        System.out.println(Optional.ofNullable(redisTemplate.opsForValue().get(KEY1)).orElse(0));
    }

    /**
     * string
     * increment
     */
    @Test
    public void test02() {
        System.out.println(redisTemplate.opsForValue().increment(KEY1, 1));
    }

    /**
     * set
     * add
     */
    @Test
    public void test03() {
        Long var = 12345678900000L;
        redisTemplate.opsForSet().add(KEY2, var);
    }

}
