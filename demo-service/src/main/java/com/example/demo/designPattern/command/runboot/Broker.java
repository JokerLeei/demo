package com.example.demo.designPattern.command.runboot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijiawei04
 * @date: 2020-09-16 14:57
 * @description: 命令调用类
 */
public class Broker {

    private List<Order> orderList = new ArrayList<>();

    public void takeOrder(Order order){
        orderList.add(order);
    }

    public void placeOrders(){
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
