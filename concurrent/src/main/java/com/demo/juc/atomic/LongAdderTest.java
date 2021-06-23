package com.demo.juc.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * {@link LongAdder}
 *
 * @author: lijiawei04
 * @date: 2021/6/3 10:16 上午
 */
public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        // toString(); 里面调用了 sum();
        System.out.println("toString():" + adder); // 0
        System.out.println("sum():" + adder.sum());// 0

        // increment(n); 里面调用实际是 add(n);
        adder.increment();
        System.out.println("+1:" + adder);   // 1

        adder.add(3);
        System.out.println("+3:" + adder);   // 4

        // 每个cell重置为0，返回void
        adder.reset();
        System.out.println("归零:" + adder);

        // 相比于reset()方法，会把reset后的值返回，即返回0
        adder.sumThenReset();
    }

}
