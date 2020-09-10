package com.example.demo.designPattern.observer.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 15:52
 * @description: 观察者B
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Octal String: " + Integer.toOctalString(subject.getState()));
    }

}
