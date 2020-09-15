package com.example.demo.aop;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 16:48
 * @description:
 */
@Data
@AopTypeAnnotation(value = "AopParam")
public class AopParam {

    private String message;
}
