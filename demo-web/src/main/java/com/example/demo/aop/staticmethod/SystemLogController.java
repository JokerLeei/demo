package com.example.demo.aop.staticmethod;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020/12/15 10:33 上午
 */
@Slf4j
@RestController
@RequestMapping("/aop/static")
public class SystemLogController {

    @RequestMapping("/test")
    public String test() {
        return SystemLogController.staticMethod("success");
    }

    @SystemLog(description = "hhh")
    public static String staticMethod(String param) {
        log.info("aop static test... staticMethod param is:[{}]", param);
        return param;
    }

}
