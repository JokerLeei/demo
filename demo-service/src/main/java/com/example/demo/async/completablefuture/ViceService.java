package com.example.demo.async.completablefuture;

import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2020-09-27 15:02
 * @description:
 */
@Slf4j
@Service
public class ViceService {

    public String viceMethod(Object... args) {
        log.info("子线程开始执行... now is:{}", LocalDateTime.now());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        if (true) {
//            throw new RuntimeException("");
//        }

        log.info("子线程执行方法... args is:{}", JSONObject.toJSONString(args));

        log.info("子线程执行结束... now is:{}", LocalDateTime.now());
        return "ok";
    }

}
