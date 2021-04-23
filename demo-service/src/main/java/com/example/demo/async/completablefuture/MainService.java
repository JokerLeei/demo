package com.example.demo.async.completablefuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-27 15:01
 * @description:
 */
@Slf4j
@Service
public class MainService {

    private final ViceService viceService;

    private final Executor executor;

    public String doSomething() {
        log.info("主逻辑开始...... now is:{}", LocalDateTime.now());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> viceService.viceMethod("hhh"), executor);

        log.info("主逻辑其他逻辑执行...... now is:{}", LocalDateTime.now());

        String result = future.join();
        log.info("副逻辑执行结束... 结果:{}", result);

        log.info("主逻辑结束...... now is:{}", LocalDateTime.now());

        return "ok";
    }

    @Autowired
    public MainService(ViceService viceService,
                       @Qualifier(value = "asyncExecutor") Executor executor) {
        this.viceService = viceService;
        this.executor = executor;
    }
}
