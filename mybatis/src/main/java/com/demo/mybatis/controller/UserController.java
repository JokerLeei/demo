package com.demo.mybatis.controller;

import com.demo.mybatis.controller.request.InsertUserRequest;
import com.demo.mybatis.mapper.ds1.UserMapper;
import com.demo.mybatis.model.User;
import com.demo.mybatis.util.DateUtil;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/1 4:00 下午
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public String insertUser(@RequestBody InsertUserRequest request) {
        User user = new User()
                .setName(request.getName())
                .setAge(request.getAge())
                .setBirthday(DateUtil.string2LocalDateTimeByDefault(request.getBirthday()));

        userMapper.insert(user);

        return "ok";
    }

}