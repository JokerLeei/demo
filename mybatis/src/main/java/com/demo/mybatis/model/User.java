package com.demo.mybatis.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: lijiawei04
 * @date: 2021/3/24 5:59 下午
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private Integer age;

    private LocalDateTime birthday;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
