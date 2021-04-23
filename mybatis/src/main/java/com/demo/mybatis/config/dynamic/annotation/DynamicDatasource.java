package com.demo.mybatis.config.dynamic.annotation;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态切换datasource，或者主从读写分离
 *
 * @author: lijiawei04
 * @date: 2021/4/14 1:52 下午
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDatasourceHolder.getDataSource();
    }

}
