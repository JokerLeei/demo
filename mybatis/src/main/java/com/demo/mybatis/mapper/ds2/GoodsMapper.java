package com.demo.mybatis.mapper.ds2;

import com.demo.mybatis.config.dynamic.annotation.DataSource;
import com.demo.mybatis.config.constant.DataSourceType;
import com.demo.mybatis.model.Goods;

import org.apache.ibatis.annotations.Param;

/**
 * @author: lijiawei04
 * @date: 2021/4/13 5:38 下午
 */
@DataSource(DataSourceType.LOCAL)
public interface GoodsMapper {

    /**
     * 增
     */
    int insert(@Param("goods")Goods goods);

    /**
     * 删
     */
    int deleteById(@Param("id") Long id);

    /**
     * 改
     */
    int updateById(@Param("id") Long id);

    /**
     * 查
     */
    Goods selectById(@Param("id") Long id);

}
