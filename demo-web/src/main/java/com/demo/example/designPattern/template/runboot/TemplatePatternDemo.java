package com.demo.example.designPattern.template.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-18 01:41
 * @description:
 */
public class TemplatePatternDemo {

    public static void main(String[] args) {
        Game game1 = new CricketGame();
        game1.play();

        Game game2 = new Football();
        game2.play();
    }
}
