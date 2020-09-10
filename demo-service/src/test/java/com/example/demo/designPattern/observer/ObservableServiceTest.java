package com.example.demo.designPattern.observer;

import com.example.demo.designPattern.observer.jdk.springboot.ObservableService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObservableServiceTest {

    @Resource
    private ObservableService observableService;

    @Test
    public void doSomething() {
        observableService.doSomething();
    }
}