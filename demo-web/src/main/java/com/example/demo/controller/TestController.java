package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-07-29 17:02
 * @description:
 */
@RestController
@RequestMapping("/")
@Slf4j
public class TestController {

    @GetMapping("test")
    public void test() {

    }
}
