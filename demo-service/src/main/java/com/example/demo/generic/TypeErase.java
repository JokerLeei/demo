package com.example.demo.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>类型擦出</h2>
 *
 * @author: lijiawei04
 * @date: 2020/10/22 11:15 上午
 */
public class TypeErase {

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        // true
        System.out.println(integerList.getClass() == stringList.getClass());
    }

}
