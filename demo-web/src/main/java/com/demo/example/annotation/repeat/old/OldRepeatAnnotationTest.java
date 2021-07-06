package com.demo.example.annotation.repeat.old;

import lombok.SneakyThrows;

/**
 * @author: lijiawei04
 * @date: 2021/2/4 4:45 下午
 */
public class OldRepeatAnnotationTest {

    @SneakyThrows
    public static void main(String[] args) {
        OldRepeatAnnotations annotations = OldRepeatAnnotationTest.class.getMethod("func").getDeclaredAnnotation(OldRepeatAnnotations.class);
        for (OldRepeatAnnotation a : annotations.value()) {
            System.out.println(a.value());
        }
    }

    @OldRepeatAnnotations( {
        @OldRepeatAnnotation("a"),
        @OldRepeatAnnotation("b"),
        @OldRepeatAnnotation("c")
    })
    public static void func() {
    }

}
