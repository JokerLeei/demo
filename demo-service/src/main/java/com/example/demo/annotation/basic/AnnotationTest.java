package com.example.demo.annotation.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

/**
 * @author: lijiawei04
 * @date: 2020-09-10 13:52
 * @description:
 */
public class AnnotationTest {

    public static void main(String[] args) throws Exception {
        // 类上的注解
        TypeAnnotation typeAnnotation = AnnotationObject.class.getAnnotation(TypeAnnotation.class);
        System.out.println("类上的注解值: " + typeAnnotation.value());

        // 方法上的注解
        Method method = AnnotationObject.class.getDeclaredMethod("annotationMethod", String.class);
        MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
        System.out.println("方法上的注解值: " + methodAnnotation.value());

        // 参数上的注解
        Parameter[] parameters = method.getParameters();
        Stream.of(parameters).filter(parameter -> {
            ParameterAnnotation parameterAnnotation = parameter.getAnnotation(ParameterAnnotation.class);
            return parameterAnnotation != null;
        }).forEach(System.out::println);

        // 字段上的注解
        Field field = AnnotationObject.class.getDeclaredField("annotationField");
        FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
        System.out.println("字段上的注释值: " + fieldAnnotation.value());

    }

}

@TypeAnnotation(value = "type")
class AnnotationObject {

    @FieldAnnotation(value = "field")
    private String annotationField;

    @MethodAnnotation(value = "method")
    private void annotationMethod(@ParameterAnnotation(value = "param") String param) {
        System.out.println("execute AnnotationMethod... param is:" + param);
    }

}