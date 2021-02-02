package com.example.demo.algorithm.linklist;

/**
 * 单向链表数据结构
 *
 * @author: lijiawei04
 * @date: 2021/2/1 10:47 上午
 */
public class Node {

    Integer value;

    Node next;

    Node(Integer value) {
        this.value = value;
    }

    public static void print(Node node) {
        if (node == null) {
            return;
        }
        if (node.next == null) {
            System.out.println(node.value);
            return;
        }
        System.out.print(node.value + " => ");
        print(node.next);
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}