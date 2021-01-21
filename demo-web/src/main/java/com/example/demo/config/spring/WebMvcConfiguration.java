package com.example.demo.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020/10/13 2:51 下午
 */
@Slf4j
@EnableWebMvc
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

}
