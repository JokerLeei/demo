package com.demo.redis.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

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
        List<DTO> list = new ArrayList<>();
        list.add(new DTO().setName("hahaha").setValue(123));
        list.add(new DTO().setName("aaaaa").setValue(100));
        String jsonStr = JSON.toJSONString(list);
        System.out.println(jsonStr);
        redisTemplate.opsForValue().set("testJson", jsonStr);
    }

    @Test
    public void test2() {
        String jsonStr = redisTemplate.opsForValue().get("testJson");
        System.out.println(jsonStr);
        List<DTO> list = JSON.parseObject(jsonStr, new TypeReference<List<DTO>>(){});
        System.out.println(list.get(0).getName());
        System.out.println(list.get(1));
    }

    public static void main(String[] args) {

    }

    @SneakyThrows
    private static void sleep(long mills) {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    @Data
    @Accessors(chain = true)
    static class DTO {
        private String name;
        private Integer value;
    }

}
