package com.example.demo.designPattern.command.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-16 14:48
 * @description:
 */
public class SellStock implements Order {

    private Stock abcStock;

    public SellStock(Stock abcStock) {
        this.abcStock = abcStock;
    }

    @Override
    public void execute() {
        abcStock.sell();
    }
}
