package com.example.demo.algorithm.linklist;

/**
 * 双向链表数据结构
 *
 * @author: lijiawei04
 * @date: 2021/2/1 10:47 上午
 */
public class DoubleNode {

    Integer value;

    DoubleNode pre;
    DoubleNode next;

    DoubleNode(Integer value) {
        this.value = value;
    }

    /**
     * 打印双向链表
     */
    public static void print(DoubleNode doubleNode) {
        if (doubleNode == null) {
            return;
        }
        System.out.println(doubleNode.value + "\t[head:" + doubleNode.pre + "]\t[next:]");
        print(doubleNode.next);
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}
