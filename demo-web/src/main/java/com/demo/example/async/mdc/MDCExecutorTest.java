package com.demo.example.async.mdc;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
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
    @Resource
    private OtherClass otherClass;

    @RequestMapping("/")
    public String test() {
        log.info("main start");

        Future<String> async = otherClass.async();
        try {
            log.warn("async result:[{}]", async.get());
        } catch (Exception e) {
            log.error("async result error!", e);
        }


        CompletableFuture.runAsync(() -> {
            log.info("async start");

            // 这里 1/0 报错不会打出任何日志
            // 除非 1.这里被try-catch 2.使用supplyAsync，在join()、get()时候会出异常
            int i = 1/0;

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("async end");
        }, executor);

        log.info("main end");

        return "SUCCESS!";
    }

}

@Slf4j
@Component
class OtherClass {

    @Async(value = "asyncExecutor")
    public Future<String> async() {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("aa");
    }
}
