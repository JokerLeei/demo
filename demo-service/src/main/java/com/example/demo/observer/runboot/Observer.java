package com.example.demo.observer.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 15:22
 * @description: 观察者接口
 */
public abstract class Observer {

    protected Subject subject;

    public abstract void update();

}
