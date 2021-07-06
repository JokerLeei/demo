package com.demo.juc.set;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 总结：https://blog.csdn.net/CSDN_WYL2016/article/details/109577341
 * ConcurrentHashMap.newKeySet() 和 Collections.newSetFromMap(new ConcurrentHashMap<>()) 适合写多读少
 * 20w元素，基本
 *
 * @author: lijiawei04
 * @date: 2021/6/18 10:39 上午
 */
public class ThreadSafeSet {
    public static void main(String[] args) throws InterruptedException {

        //Set<String> set = ConcurrentHashMap.newKeySet();

        Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<>());

        //CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet();

        readMoreWriteLess(set);

        System.out.println("==========华丽的分隔符==========");

        //set = ConcurrentHashMap.newKeySet();

        set = Collections.newSetFromMap(new ConcurrentHashMap<>());

        //set = new CopyOnWriteArraySet();

        writeMoreReadLess(set);
    }

    private static void writeMoreReadLess(Set<String> set) throws InterruptedException {
        //测20组
        for (int k = 1; k <= 20; k++) {
            CountDownLatch countDownLatch = new CountDownLatch(10);
            long start = System.currentTimeMillis();
            //创建9个线程，每个线程向set中写1000条数据
            for (int i = 0; i < 9; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        set.add(UUID.randomUUID().toString());
                    }
                    countDownLatch.countDown();
                }).start();
            }

            //创建1个线程，每个线程从set中读取所有数据，每个线程一共读取10次。
            for (int i = 0; i < 1; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10; j++) {
                        Iterator<String> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            iterator.next();
                        }
                    }
                    countDownLatch.countDown();
                }).start();
            }
            //阻塞，直到10个线程都执行结束
            countDownLatch.await();
            long end = System.currentTimeMillis();
            System.out.println("写多读少：第" + k + "次执行耗时：" + (end - start) + "毫秒" + "，容器中元素个数为：" + set.size());
        }
    }

    private static void readMoreWriteLess(Set<String> set) throws InterruptedException {
        //测20组
        for (int k = 1; k <= 20; k++) {
            CountDownLatch countDownLatch = new CountDownLatch(10);
            long start = System.currentTimeMillis();
            //创建1个线程，每个线程向set中写10条数据
            for (int i = 0; i < 1; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10; j++) {
                        set.add(UUID.randomUUID().toString());
                    }
                    countDownLatch.countDown();
                }).start();
            }

            // 创建9个线程，每个线程从set中读取所有数据，每个线程一共读取100万次。
            for (int i = 0; i < 9; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 1000000; j++) {
                        Iterator<String> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            iterator.next();
                        }
                    }
                    countDownLatch.countDown();
                }).start();
            }
            countDownLatch.await();
            long end = System.currentTimeMillis();
            System.out.println("读多写少：第" + k + "次执行耗时：" + (end - start) + "毫秒" + "，容器中元素个数为：" + set.size());
        }
    }
}