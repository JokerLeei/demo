package com.example.demo.designPattern.command.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-16 14:47
 * @description:
 */
public class BuyStock implements Order {

    private Stock abcStock;

    public BuyStock(Stock abcStock) {
        this.abcStock = abcStock;
    }

    @Override
    public void execute() {
        abcStock.buy();
    }
}
