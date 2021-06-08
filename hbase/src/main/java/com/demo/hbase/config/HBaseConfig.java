package com.demo.hbase.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云 HBase
 *
 * @author: lijiawei04
 * @date: 2021/5/14 10:28 上午
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class HBaseConfig {

    @Bean(destroyMethod = "close")
    public Connection hbaseConnection() throws IOException {
        // 新建一个Configuration
        Configuration conf = HBaseConfiguration.create();
        // 集群的连接地址，在控制台页面的数据库连接界面获得(注意公网地址和VPC内网地址)
        conf.set("hbase.zookeeper.quorum", "https://sh-2zeeh972cvt8s4042-lindorm-serverless.lindorm.rds.aliyuncs.com:443");
        // AccessKey Id
        conf.set("hbase.client.username", "LTAI5t6YMh6WBnS4yX9BuvbY");
        // AccessKey Secret
        conf.set("hbase.client.password", "OkYPwddyjI511ZXwkdhwnDxm4KFPpo");

        // 如果您直接依赖了阿里云hbase客户端，则无需配置connection.impl参数，如果您依赖了alihbase-connector，则需要配置此参数
        // conf.set("hbase.client.connection.impl", AliHBaseUEClusterConnection.class.getName());

        // 创建 HBase连接，在程序生命周期内只需创建一次，该连接线程安全，可以共享给所有线程使用。
        // 在程序结束后，需要将Connection对象关闭，否则会造成连接泄露。
        // 也可以采用try finally方式防止泄露
        Connection connection = ConnectionFactory.createConnection(conf);
        log.info(">>>>> hbase configuration create success!");
        return connection;
    }

}
