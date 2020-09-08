package com.example.demo.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 16:14
 * @description:
 */
public class BObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        NumObservable observable = (NumObservable) o;
        System.out.println("B Observer... data changed! data is:" + observable.getData());
    }

}
