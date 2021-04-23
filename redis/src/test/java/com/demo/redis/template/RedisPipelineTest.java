package com.demo.redis.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: lijiawei04
 * @date: 2021/4/20 12:03 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisPipelineTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test1() {

    }

}
