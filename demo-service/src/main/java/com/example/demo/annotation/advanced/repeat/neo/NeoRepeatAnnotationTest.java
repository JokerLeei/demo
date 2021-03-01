package com.example.demo.annotation.advanced.repeat.neo;

import lombok.SneakyThrows;

/**
 * @author: lijiawei04
 * @date: 2021/2/4 5:32 下午
 */
public class NeoRepeatAnnotationTest {

    @SneakyThrows
    public static void main(String[] args) {
        NeoRepeatAnnotation[] annotations = NeoRepeatAnnotationTest.class.getMethod("func").getAnnotationsByType(NeoRepeatAnnotation.class);
        for (NeoRepeatAnnotation a : annotations) {
            System.out.println(a.value());
        }
    }

    @NeoRepeatAnnotation("a")
    @NeoRepeatAnnotation("b")
    @NeoRepeatAnnotation("c")
    public static void func() {
    }

}
