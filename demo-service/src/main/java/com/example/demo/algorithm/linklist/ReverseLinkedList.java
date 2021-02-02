package com.example.demo.algorithm.linklist;

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

        DoubleNode.print(doubleNode1);
        reverse(doubleNode1);
        DoubleNode.print(doubleNode3);


        System.out.println("===================================");

        // 反转单向链表
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;
        Node.print(node1);
        reverse(node1);
        Node.print(node3);
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

}
