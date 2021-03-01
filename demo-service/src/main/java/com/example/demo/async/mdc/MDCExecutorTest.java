package com.example.demo.async.mdc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/2/3 6:38 下午
 */
@Slf4j
@RestController
@RequestMapping("/")
public class MDCExecutorTest {

    @Resource(name = "asyncExecutor")
    private Executor executor;

    @RequestMapping("/")
    public String test() {
        log.info("main start");
        CompletableFuture.runAsync(() -> {
            log.info("sub start");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("sub end");
        }, executor);

        log.info("main end");

        return "SUCCESS!";
    }

}
