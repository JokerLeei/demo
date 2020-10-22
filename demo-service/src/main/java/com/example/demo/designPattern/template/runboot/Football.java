package com.example.demo.designPattern.template.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-18 01:40
 * @description:
 */
public class Football extends Game {

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }
}
