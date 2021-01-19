package com.example.demo.algorithm;

/**
 * 反转单向链表
 * 反转双向链表
 *
 * 时间 O(n): N
 * 空间 O(n): 1
 *
 * @author: lijiawei04
 * @date: 2021/1/18 4:11 下午
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        // 反转双向链表
        DoubleNode doubleNode1 = new DoubleNode(1);
        DoubleNode doubleNode2 = new DoubleNode(2);
        DoubleNode doubleNode3 = new DoubleNode(3);
        doubleNode1.next = doubleNode2;
        doubleNode2.pre = doubleNode1;
        doubleNode2.next = doubleNode3;
        doubleNode3.pre = doubleNode2;

        print(doubleNode1);
        reverse(doubleNode1);
        print(doubleNode3);


        System.out.println("===================================");

        // 反转单向链表
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;
        print(node1);
        reverse(node1);
        print(node3);
    }

    public static void reverse(Node node) {
        Node pre = null;            // 当前结点的前结点
        Node next = null;           // 当前结点的后结点
        while (node != null) {      // 从左到右, 依次把 -> 变为 <-
            next = node.next;
            node.next = pre;        // 当前结点指向前一个结点
            pre = node;             // pre 右移
            node = next;            // node 右移
        }
    }

    public static void reverse(DoubleNode node) {
        DoubleNode pre = null;      // 当前结点的前结点
        DoubleNode next = null;     // 当前结点的后结点
        while (node != null) {      // 从左到右, 依次把 -> 变为 <-
            next = node.next;
            node.next = pre;        // -> 改为 <-
            node.pre = next;        // <- 改为 ->
            pre = node;             // pre 右移
            node = next;            // node 右移
        }
    }

    public static void print(DoubleNode doubleNode) {
        if (doubleNode == null) {
            return;
        }
        System.out.println(doubleNode.value + " [head:" + doubleNode.pre + "] [tail:" + doubleNode.next + "]");
        print(doubleNode.next);
    }

    public static void print(Node node) {
        if (node == null) {
            System.out.println();
            return;
        }
        if (node.next == null) {
            System.out.println(node.value);
            return;
        }
        System.out.print(node.value + " => ");
        print(node.next);
    }

}

class Node {
    Integer value;

    Node next;

    Node(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}

class DoubleNode {
    Integer value;

    DoubleNode pre;
    DoubleNode next;

    DoubleNode(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}
