package com.demo.hbase.domain.table;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo.hbase.domain.columnfamily.ColumnFamily;
import com.demo.hbase.domain.rowkey.RowKey;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * HBase Table
 *
 * @author: lijiawei04
 * @date: 2021/5/14 4:59 下午
 */
public interface Table {

    /**
     * 获取 HBase namespace
     *
     * @return namespace
     */
    @JSONField(serialize = false)
    default String getNamespace() {
        return "default";
    }

    /**
     * 获取 HBase table name
     *
     * @return 表名
     */
    @JSONField(serialize = false)
    String getTableName();

    /**
     * 获取 HBase 表的 RowKey
     *
     * @return HBase Table RowKey
     */
    @JSONField(serialize = false)
    RowKey getRowKey();

    /**
     * 获取 HBase 表的 ColumnFamily
     *
     * @return HBase table ColumnFamily
     */
    @JSONField(serialize = false)
    List<ColumnFamily> getColumnFamily();

    /**
     * 获取表全名
     *
     * @return 表全名
     */
    @JSONField(serialize = false)
    default String getFullName() {
        return StringUtils.join(getNamespace(), ":", getTableName());
    }

}
