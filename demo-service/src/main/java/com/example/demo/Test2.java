package com.example.demo;

/**
 * @author: lijiawei04
 * @date: 2021/3/29 1:05 下午
 */
public class Test2 {

    public static void main(String[] args) {
        long userId = 710004L;
        System.out.println("test:");
        System.out.println("库:" + (userId / 1000) % 16);
        System.out.println("表:" + (userId / 16000) % 32);

        System.out.println("beta:");
        System.out.println("库:" + (userId / 1000) % 2);
        System.out.println("表:" + (userId / 2000) % 32);

        System.out.println("prod:");
        System.out.println("库:" + (userId / 1000) % 32);
        System.out.println("表:" + (userId / 32000) % 256);
    }

}
