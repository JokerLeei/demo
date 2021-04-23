package com.demo.mybatis.controller.request;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/1 4:05 下午
 */
@Data
public class InsertUserRequest {

    private String name;

    private Integer age;

    private String birthday;

}
