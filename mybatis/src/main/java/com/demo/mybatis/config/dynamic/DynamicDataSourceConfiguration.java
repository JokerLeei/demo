package com.demo.mybatis.config.dynamic;

import com.demo.mybatis.config.constant.DataSourceType;
import com.demo.mybatis.config.dynamic.annotation.DynamicDatasource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * {@link DataSource}
 * {@link SqlSessionFactory}
 *
 * @author: lijiawei04
 * @date: 2021/3/23 9:06 下午
 */
//@Configuration
@PropertySource(ignoreResourceNotFound = true, value = { "classpath:jdbc.properties" })
// ⬇ 读取的mapper包里包含不同的数据源
@MapperScan(basePackages = { "com.demo.mybatis.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class DynamicDataSourceConfiguration {

    /**
     * dataSource1 (RDS)
     */
    @Bean(name = DataSourceType.RDS)
    public DataSource rdsDataSource(
            @Value("${rds.jdbc.driver:}")       String driver,
            @Value("${rds.jdbc.url:}")          String jdbcUrl,
            @Value("${rds.jdbc.username:}")     String username,
            @Value("${rds.jdbc.password:}")     String password) {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    /**
     * dataSource2 (localhost)
     */
    @Bean(name = DataSourceType.LOCAL)
    public DataSource localDataSource(
            @Value("${local.jdbc.driver:}")        String driver,
            @Value("${local.jdbc.url:}")           String jdbcUrl,
            @Value("${local.jdbc.username:}")      String username,
            @Value("${local.jdbc.password:}")      String password) {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    /**
     * dynamic datasource
     */
    @Bean(name = "dynamicDataSource")
    public DynamicDatasource dynamicDataSource(
            @Qualifier(DataSourceType.RDS) DataSource dataSource,
            @Qualifier(DataSourceType.LOCAL) DataSource dataSource2) {
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put(DataSourceType.RDS, dataSource);
        dsMap.put(DataSourceType.LOCAL, dataSource2);
        dynamicDatasource.setTargetDataSources(dsMap);
        dynamicDatasource.setDefaultTargetDataSource(dataSource);
        return dynamicDatasource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
