package com.example.demo.designPattern.template.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-18 01:39
 * @description:
 */
public class CricketGame extends Game {

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }

    @Override
    void endPlay() {
        System.out.println("Cricket Game Finished!");
    }
}
