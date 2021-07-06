package com.demo.example.designPattern.observer.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 15:53
 * @description:
 */
public class HexaObserver extends Observer {

    public HexaObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Hex String: " + Integer.toHexString(subject.getState()).toUpperCase());
    }

}
