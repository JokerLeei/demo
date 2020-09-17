package com.example.demo.designPattern.command.runboot;

/**
 * @author: lijiawei04
 * @date: 2020-09-16 15:00
 * @description:
 */
public class CommandPatternDemo {

    public static void main(String[] args) {
        Stock stock = new Stock();

        BuyStock buyStock = new BuyStock(stock);
        SellStock sellStock = new SellStock(stock);

        Broker broker = new Broker();
        broker.takeOrder(buyStock);
        broker.takeOrder(sellStock);

        broker.placeOrders();
    }
}
