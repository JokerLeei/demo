package com.demo.example.designPattern.observer.jdk.springboot;

import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 17:22
 * @description:
 */
@Slf4j
@Component
public class BObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        // 可以获取被观察对象
        ObservableService service = (ObservableService) o;
        log.info("B observer... observable is:{}, transfer data is:{}", service, arg);
    }

}
