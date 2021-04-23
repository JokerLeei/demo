package com.demo.mybatis.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/13 5:36 下午
 */
@Data
public class Goods {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
