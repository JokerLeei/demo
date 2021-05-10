package com.demo.mybatis.config.base;

import com.demo.mybatis.config.constant.DataSourceType;
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

import javax.sql.DataSource;

/**
 * @author: lijiawei04
 * @date: 2021/4/14 2:25 下午
 */
//@Configuration
@PropertySource(ignoreResourceNotFound = true, value = { "classpath:jdbc.properties" })
// ⬇ 配置这个dataSource的mapper文件路径
@MapperScan(basePackages = { "com.demo.mybatis.mapper.ds2" }, sqlSessionFactoryRef = "localSqlSessionFactory")
public class LocalDataSourceConfiguration {

    /**
     * dataSource (localhost)
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

    @Bean(name = "localSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(value = DataSourceType.LOCAL) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier(value = DataSourceType.LOCAL) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
