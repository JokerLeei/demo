package com.example.demo.controller;

import com.example.demo.async.completablefuture.MainService;
import com.example.demo.designPattern.observer.eventListener.Request;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-07-29 17:02
 * @description: 测试 controller1
 */
@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TestController {

    /**
     * 注入ApplicationEventPublisher用来发布事件
     */
    private final ApplicationEventPublisher publisher;

    private final MainService mainService;

    @PostMapping("listener")
    public void listener(@RequestBody Request request) {
        log.info("主程序运行开始... request:{}, now:{}", request, LocalDateTime.now());
        log.info("主程序运行结束... now:{}", LocalDateTime.now());
    }

    @PostMapping("test")
    public String test(String param) {
        return param;
    }

    @GetMapping("exception")
    public void exception() {
        throw new RuntimeException("aop exception");
    }

    @GetMapping("async")
    public void async() {
        mainService.doSomething();
    }

}
