package com.example.demo.algorithm.binarytree;

/**
 * 水平翻转二叉树
 *
 * @author: lijiawei04
 * @date: 2021/2/1 10:45 上午
 */
public class InvertBinaryTree {

    /**
     *       4                    4
     *      / \                  / \
     *     2   7       ==>      7   2
     *   / \  / \             / \  / \
     *  1  3 6   9           9  6 3   1
     */
    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(4);
        BinaryTree rootLeft = new BinaryTree(2);
        BinaryTree rootRight = new BinaryTree(7);
        root.left = rootLeft;
        root.right = rootRight;

        rootLeft.left = new BinaryTree(1);
        rootLeft.right = new BinaryTree(3);
        rootRight.left = new BinaryTree(6);
        rootRight.right = new BinaryTree(9);

        BinaryTree.preForeach(root);
        System.out.println();
//        BinaryTree.midForeach(root);
//        System.out.println();
//        BinaryTree.sufForeach(root);
//        System.out.println();

        // 水平翻转二叉树
        invertBinaryTree(root);

        BinaryTree.preForeach(root);
    }

    public static void invertBinaryTree(BinaryTree root) {
        if (root == null) {
            return;
        }
        BinaryTree temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertBinaryTree(root.left);
        invertBinaryTree(root.right);
    }

}
