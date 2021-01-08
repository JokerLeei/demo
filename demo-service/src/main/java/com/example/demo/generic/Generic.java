package com.example.demo.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * <h1>泛型类</h1>
 *
 * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型<br>
 * 在实例化泛型类时，必须指定T的具体类型
 *
 * @author: lijiawei04
 * @date: 2020/10/22 4:59 下午
 */
@Slf4j
public class Generic<T> {

    // key这个成员变量的类型为T,T的类型由外部指定
    private final T key;

    // 泛型构造方法形参key的类型也为T，T的类型由外部指定
    public Generic(T key) {
        this.key = key;
    }

    // 泛型类的成员方法getKey的返回值类型为T，T的类型由外部指定
    public T getKey(){
        return key;
    }

    public static void main(String[] args) {
        // 泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
        // 传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<>(123456);

        // 传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> genericString = new Generic<>("key_vlaue");
        log.info("泛型测试 key is:[{}]", genericInteger.getKey());
        log.info("泛型测试 key is:[{}]", genericString.getKey());
    }

}

/**
 * <h1>泛型方法</h1>
 */
class GenericMethod {

    public <T> T genericMethod(Class<T> tClass) {
        return null;
    }

}