package com.demo.hbase.util;

import org.apache.hadoop.hbase.client.Connection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * HBase 工具类
 *
 * @author: lijiawei04
 * @date: 2021/5/14 4:36 下午
 */
@Slf4j
@Component
public class HBaseClient {

    @Resource(name = "hbaseConnection")
    private Connection connection;

//    private

}
