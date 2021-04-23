package com.demo.mybatis.config.dynamic.annotation;

import com.demo.mybatis.config.constant.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态切换数据源
 *
 * @author: lijiawei04
 * @date: 2021/4/14 1:46 下午
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * datasource bean name {@link DataSourceType}
     *
     * @return datasource bean name
     */
    String value();

}
