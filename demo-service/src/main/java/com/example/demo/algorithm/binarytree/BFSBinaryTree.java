package com.example.demo.algorithm.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 宽度优先遍历二叉树
 *
 * @author: lijiawei04
 * @date: 2021/1/26 1:15 下午
 */
public class BFSBinaryTree {

    /**
     *        1
     *       / \
     *     2    3
     *      \  / \
     *      4 5  6
     */
    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(1);
        BinaryTree rootLeft = new BinaryTree(2);
        BinaryTree rootRight = new BinaryTree(3);
        root.left = rootLeft;
        root.right = rootRight;

        rootLeft.right = new BinaryTree(4);
        rootRight.left = new BinaryTree(5);
        rootRight.right = new BinaryTree(6);

        BFS(root);
    }

    public static void BFS(BinaryTree root) {
        if (root == null) {
            return;
        }
        // 初始化queue
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()) {
            BinaryTree treeNode = queue.poll();
            if (treeNode == null) {
                break;
            }
            System.out.print(treeNode.value + " ");

            if (treeNode.left != null) {
                queue.add(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
            }
        }
    }

}

