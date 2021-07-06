package com.demo.example.designPattern.observer.jdk;

import java.util.Observable;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 16:15
 * @description: [被观察者] 当其中某个方法被调用后, 通知所有观察者并调用对应方法
 * 在这里详细描述为: 当data值改变后通知所有观察者
 */
public class NumObservable extends Observable {

    private int data = 0;

    public int getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = data;
        this.setChanged();
        this.notifyObservers();
    }

}
