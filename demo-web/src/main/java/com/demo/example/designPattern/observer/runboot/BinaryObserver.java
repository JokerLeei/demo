package com.demo.example.designPattern.observer.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 15:51
 * @description: 观察者A
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Binary String: " + Integer.toBinaryString(subject.getState()));
    }
}