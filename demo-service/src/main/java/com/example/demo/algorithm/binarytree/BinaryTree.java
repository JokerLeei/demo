package com.example.demo.algorithm.binarytree;

/**
 * 二叉树数据结构
 *
 * @author: lijiawei04
 * @date: 2021/2/1 10:46 上午
 */
public class BinaryTree {

    Integer value;

    BinaryTree left;
    BinaryTree right;

    BinaryTree(Integer value) {
        this.value = value;
    }

    /**
     * 前序遍历打印
     */
    public static void preForeach(BinaryTree tree) {
        if (tree == null) {
            return;
        }
        System.out.print(tree.value + " ");

        preForeach(tree.left);
        preForeach(tree.right);
    }

    /**
     * 中序遍历打印
     */
    public static void midForeach(BinaryTree tree) {
        if (tree == null) {
            return;
        }
        midForeach(tree.left);

        System.out.print(tree.value + " ");

        midForeach(tree.right);
    }

    /**
     * 后续遍历
     */
    public static void sufForeach(BinaryTree tree) {
        if (tree == null) {
            return;
        }
        sufForeach(tree.left);
        sufForeach(tree.right);

        System.out.print(tree.value + " ");
    }

    @Override
    public String toString() {
        return "BinaryTree{" + "value=" + value + ", left=" + left + ", right=" + right + '}';
    }

}
