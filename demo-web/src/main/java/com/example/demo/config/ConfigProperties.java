package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author: lijiawei04
 * @date: 2020/12/30 11:18 上午
 */
@Configuration
@PropertySource(value = "classpath:redis.properties")
public class ConfigProperties {

    @Bean
    public A getA(@Value("${server.port}") Integer port) {
        return new A(port);
    }

    static class A {
        Integer port;

        A (Integer port) {
            this.port = port;
            System.out.println(port);
        }
    }

}
