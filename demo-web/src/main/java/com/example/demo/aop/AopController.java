package com.example.demo.aop;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 14:42
 * @description:
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/aop")
public class AopController {

    @GetMapping("test")
    public String test(String param) {
        return param;
    }

    @PostMapping("annotation")
    public String annotation(AopParam body) {
        return body.getMessage();
    }

    @AopMethodAnnotation(value = "http get method")
    @GetMapping("get")
    public String get() {
        return "get";
    }

    @AopMethodAnnotation(value = "http post method")
    @PostMapping("post")
    public String post() {
        return "post";
    }

    @GetMapping("exception")
    public void exception() {
        throw new RuntimeException("aop test");
    }

    @GetMapping("testWithTwoParams")
    public String testWithTwoParams(String param1, Integer param2) {
        StringBuilder sb = new StringBuilder();
        for (; param2 > 0; param2--) {
            sb.append(param1);
        }
        return sb.toString();
    }

    @GetMapping("argNames")
    public String argNames(String param1, String param2) {
        return param1 + param2;
    }

}
