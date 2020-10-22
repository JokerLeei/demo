package com.example.demo.async.completablefuture;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020/10/9 7:36 下午
 * @description:
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) {
        log.info("main start...");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> function("abc"));

        log.info("main call function...");
        CompletableFuture<String> a = future.whenComplete((s, throwable) -> {
            System.out.println(s);
            System.out.println(throwable);
        });
        System.out.println(a.join());
        log.info("main call function finish...");

        sleep(3000);
        log.info("main end...");
    }

    public static String function(String args) {
        log.info("function start...");

        sleep(1000);

        if (false) {
            throw new RuntimeException();
        }

        log.info("function end...");
        return args;
    }

    public static void sleep(long milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            log.error("sleep interrupted!", e);
        }
    }
}
