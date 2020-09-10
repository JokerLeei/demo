package com.example.demo.designPattern.observer.jdk.springboot;

import org.springframework.stereotype.Service;

import java.util.Observable;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 17:04
 * @description: 被观察service
 */
@Slf4j
@Service
@AllArgsConstructor
public class ObservableService extends Observable {

    private final AObserver aObserver;

    private final BObserver bObserver;

    @PostConstruct
    public void init() {
        // 本对象构造完成时将注入的观察者对象加入被观察者的容器
        this.addObserver(aObserver);
        this.addObserver(bObserver);
    }

    public void doSomething() {
        log.info("ObservableService doSomething... this is:{}", this);
        // 通知观察者
        this.setChanged();
        this.notifyObservers("参数");
    }

}
