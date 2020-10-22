package com.example.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-22 11:08
 * @description: 调用controller前后打印日志（调用前的请求参数及调用成功后的返回值）
 */
@Slf4j
@Aspect
@Component
public class LogAspect {



}
