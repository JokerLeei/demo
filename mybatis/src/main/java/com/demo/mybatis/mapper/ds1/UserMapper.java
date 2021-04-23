package com.demo.mybatis.mapper.ds1;

import com.demo.mybatis.config.dynamic.annotation.DataSource;
import com.demo.mybatis.config.constant.DataSourceType;
import com.demo.mybatis.model.User;

import org.apache.ibatis.annotations.Param;

/**
 * @author: lijiawei04
 * @date: 2021/3/25 3:04 下午
 */
@DataSource(DataSourceType.RDS)
public interface UserMapper {

    /**
     * 增
     */
    int insert(@Param("user") User user);

    /**
     * 删
     */
    int deleteById(@Param("id") Long id);

    /**
     * 改
     */
    int updateById(@Param("user") User user);

    /**
     * 查
     */
    User selectById(@Param("id") Long id);

}
