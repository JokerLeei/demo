package com.example.demo.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-14 14:29
 * @description:
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = {
        Exception.class
    })
    String handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable e) throws Throwable {
        log.error("{}", e.getMessage(), e);
        return "unknown error!";
    }

}
