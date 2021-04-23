package com.demo.mybatis.controller;

import com.demo.mybatis.controller.request.InsertGoodsRequest;
import com.demo.mybatis.mapper.ds2.GoodsMapper;
import com.demo.mybatis.model.Goods;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: lijiawei04
 * @date: 2021/4/14 10:27 上午
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsMapper goodsMapper;

    @PostMapping("insert")
    public String insertGoods(@RequestBody InsertGoodsRequest request) {
        Goods goods = new Goods();
        goods.setName(request.getName());
        goods.setPrice(request.getPrice());
        goods.setStock(request.getStock());
        goodsMapper.insert(goods);

        return "ok";
    }

}
