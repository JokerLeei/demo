package com.example.demo.controller;

import com.example.demo.observer.eventListener.AccessEvent;
import com.example.demo.observer.eventListener.Request;

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
 * @description: 测试 controller
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

    @PostMapping("listener")
    public void listener(@RequestBody Request request) {
        log.info("准备执行主程序... now:{}", LocalDateTime.now());
        publisher.publishEvent(new AccessEvent(this, request));
        log.info("原来的程序运行... request is:{}, now:{}", request, LocalDateTime.now());
    }

    @GetMapping("test")
    public String main() {
        return "test!";
    }

}
