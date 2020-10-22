package com.example.demo.designPattern.template.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-18 01:30
 * @description: 模版模式
 * https://www.runoob.com/design-pattern/template-pattern.html
 */
public abstract class Game {

    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    // 模版
    public final void play() {

        // 初始化游戏
        initialize();

        // 开始游戏
        startPlay();

        // 结束游戏
        endPlay();
    }
}
