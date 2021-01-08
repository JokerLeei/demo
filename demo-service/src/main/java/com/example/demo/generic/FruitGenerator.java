package com.example.demo.generic;

import java.util.Random;

/**
 * <h1>泛型接口的实现类(未传入泛型实参)</h1>
 * <p>
 * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中<br>
 * 即：class FruitGenerator<T> implements Generator<T>{<br>
 * 如果不声明泛型，如：class FruitGenerator implements Generator<T>，编译器会报错："Unknown class"
 *
 * @author: lijiawei04
 * @date: 2020/10/23 4:33 下午
 */
public class FruitGenerator<T> implements Generator<T> {

    @Override
    public T next() {
        return null;
    }

}

/**
 * 传入泛型实参时：<br>
 * 定义一个生产器实现这个接口,虽然我们只创建了一个泛型接口Generator<T><br>
 * 但是我们可以为T传入无数个实参，形成无数种类型的Generator接口。<br>
 * 在实现类实现泛型接口时，如已将泛型类型传入实参类型，则所有使用泛型的地方都要替换成传入的实参类型<br>
 * 即：Generator<T>，public T next();中的的T都要替换成传入的String类型。<br>
 */
class FruitGenerator2 implements Generator<String> {

    private final String[] fruits = new String[] { "Apple", "Banana", "Pear" };

    @Override
    public String next() {
        return fruits[new Random().nextInt(fruits.length)];
    }

}