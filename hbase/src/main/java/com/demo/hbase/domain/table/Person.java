package com.demo.hbase.domain.table;

import com.demo.hbase.domain.columnfamily.ColumnFamily;
import com.demo.hbase.domain.rowkey.RowKey;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * default.person
 *
 *  rk1(userId) | cf1(friend1)  | cf2(friend2)
 *              | name:abc      | name:xyz
 *              | age:20        | sex:male
 *
 * @author: lijiawei04
 * @date: 2021/5/24 5:55 下午
 */
@Data
@Accessors(chain = true)
public class Person implements Table {

    @Override
    public String getTableName() {
        return "person";
    }

    @Override
    public RowKey getRowKey() {
        return null;
    }

    @Override
    public List<ColumnFamily> getColumnFamily() {
        return null;
    }

}
