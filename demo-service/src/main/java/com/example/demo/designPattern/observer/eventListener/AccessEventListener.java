package com.example.demo.designPattern.observer.eventListener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 15:09
 * @description: 定义事件的监听器
 */
@Slf4j
@Component
@EnableAsync
public class AccessEventListener {

    @EventListener
    @Async
    public void addAccess(AccessEvent event) {
        Request request = event.getRequest();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("事件监听器运行... request is:{}, now:{}", request, LocalDateTime.now());
    }

}
