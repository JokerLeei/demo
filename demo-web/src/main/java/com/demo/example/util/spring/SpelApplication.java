package com.demo.example.util.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

/**
 * @author: lijiawei04
 * @date: 2021/3/8 10:48 上午
 */
@PropertySource("classpath:app.properties")
@SpringBootApplication(scanBasePackages = {"com.demo.example.util.spring"})
public class SpelApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(SpelApplication.class, args);

        ConfigurableEnvironment environment = app.getEnvironment();
        System.out.println(environment.getProperty("spring.application.name") + "服务启动完毕...");
        System.out.println("--------------------测试@Value开始---------------------");
        //计算一个具体的值（非表达式）
        System.out.println(ValueUtil.resolveExpression("1121"));
        //实现@Value的功能
        System.out.println(ValueUtil.resolveExpression("${spring.application.name}"));
        System.out.println(ValueUtil.resolveExpression("${kanghe.ncdcloud.commission.testApollo:123}"));
        System.out.println(ValueUtil.resolveExpression("${ncdcloud.app.build.time:123}"));
        System.out.println(ValueUtil.resolveExpression("#{userService.count()}"));
        System.out.println(ValueUtil.resolveExpression("#{userService.max(${app.size:12})}"));
        System.out.println(ValueUtil.resolveExpression("#{${app.size:12} <= '12345'.length() ? ${app.size:12} : '12345'.length()}"));

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //实现@Value的功能
        System.out.println(ValueUtil.resolveExpression("${kanghe.ncdcloud.commission.testApollo:123}"));
        System.out.println("--------------------测试@Value结束---------------------");

    }

}

/**
 * 测试Bean
 */
@Service("userService")
class UserService {

    public int count() {
        return 10;
    }

    public int max(int size) {
        int count = count();
        return Math.max(count, size);
    }
}