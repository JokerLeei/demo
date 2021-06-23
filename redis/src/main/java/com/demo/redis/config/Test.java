package com.demo.redis.config;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

/**
 * @author: lijiawei04
 * @date: 2021/6/18 10:12 上午
 */
public class Test {

    private final Set<String> set = new CopyOnWriteArraySet<>();

    public void f() {
        set.add("abc");

        sleep(500);

        new Thread(() -> {
            System.out.println("thread:" + set);

            set.add("thread");
        }).start();

        sleep(500);

        set.add("xyz");
        System.out.println("main:" + set);
    }

    @SneakyThrows
    private void sleep(long mills) {
        TimeUnit.MILLISECONDS.sleep(mills);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.f();
    }

}
