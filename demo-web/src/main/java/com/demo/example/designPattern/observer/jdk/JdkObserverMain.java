package com.demo.example.designPattern.observer.jdk;

/**
 * @author: lijiawei04
 * @date: 2020-09-08 16:38
 * @description: jdk的观察者模式测试
 */
public class JdkObserverMain {

    public static void main(String[] args) {
        // 创建被观察者
        NumObservable observable = new NumObservable();
        // 将观察者加入被观察者
        observable.addObserver(new AObserver());
        observable.addObserver(new BObserver());

        observable.setData(1);
    }
}
