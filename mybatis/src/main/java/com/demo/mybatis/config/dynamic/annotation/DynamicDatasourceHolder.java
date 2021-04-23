package com.demo.mybatis.config.dynamic.annotation;

import org.springframework.util.Assert;

/**
 * @author: lijiawei04
 * @date: 2021/4/14 1:59 下午
 */
public class DynamicDatasourceHolder {

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    /**
     * 设置当前线程的datasource
     *
     * @param dataSource 当前线程的datasource
     */
    public static void setDataSource(String dataSource) {
        Assert.notNull(dataSource, "DataSourceType cannot be null");
        HOLDER.set(dataSource);
    }

    /**
     * 获取当前线程的dataSource
     *
     * @return 当前线程的dataSource
     */
    public static String getDataSource() {
        return HOLDER.get();
    }

    public static void clearDataSource() {
        HOLDER.remove();
    }

}
