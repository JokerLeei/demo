package com.demo.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/5/14 10:48 上午
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class HBaseTest {

    private static final String TABLE_NAME = "tb_test";

    @Resource(name = "hbaseConnection")
    private Connection connection;

    /**
     * hbase v1.0 的DDL
     * v2.0 时Deprecated
     * v3.0 会remove 掉
     */
    @Test
    public void testDDL() {
        try (Admin admin = connection.getAdmin()){
            // 建表
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            htd.addFamily(new HColumnDescriptor(Bytes.toBytes("family")));
            // 创建一个只有一个分区的表
            // 在生产上建表时建议根据数据特点预先分区
            admin.createTable(htd);

            // disable 表
            admin.disableTable(TableName.valueOf(TABLE_NAME));

            // truncate 表
            admin.truncateTable(TableName.valueOf(TABLE_NAME), true);

            // 删除表
            admin.deleteTable(TableName.valueOf(TABLE_NAME));
        } catch (IOException e) {
            log.error("test DDL error!", e);
        }
    }

    @Test
    public void testDML() {
        // Table 为非线程安全对象，每个线程在对Table操作时，都必须从Connection中获取相应的Table对象
        try (Table table = connection.getTable(TableName.valueOf(TABLE_NAME))) {
            // 插入数据
            Put put = new Put(Bytes.toBytes("row"));
            put.addColumn(Bytes.toBytes("family"), Bytes.toBytes("qualifier"), Bytes.toBytes("value"));
            table.put(put);

            // 单行读取
            Get get = new Get(Bytes.toBytes("row"));
            Result res = table.get(get);
            System.out.println(res);

            // 删除一行数据
            Delete delete = new Delete(Bytes.toBytes("row"));
            table.delete(delete);

            // scan 范围数据
            Scan scan = new Scan(Bytes.toBytes("startRow"), Bytes.toBytes("endRow"));
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                System.out.println(result);
            }
            scanner.close();
        } catch (IOException e) {
            log.error("test DML error!", e);
        }
    }

    public void testPut() {
        try (Table table = connection.getTable(TableName.valueOf(TABLE_NAME))) {
            // 插入数据
            Put put = new Put(Bytes.toBytes("row"));
            put.addColumn(Bytes.toBytes("family"), Bytes.toBytes("qualifier"), Bytes.toBytes("value"));
//            table.put();
        } catch (IOException e) {
            log.error("test DML error!", e);
        }
    }

}
