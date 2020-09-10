package com.example.demo.designPattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 16:12
 * @description: 观察者A
 */
public class AObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        NumObservable observable = (NumObservable) o;
        System.out.println("A Observer... data changed! data is:" + observable.getData());
    }

}
