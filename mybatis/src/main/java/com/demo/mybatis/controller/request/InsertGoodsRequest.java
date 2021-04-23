package com.demo.mybatis.controller.request;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/14 10:26 上午
 */
@Data
public class InsertGoodsRequest {

    private String name;

    private Integer price;

    private Integer stock;

}
