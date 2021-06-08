package com.demo.hbase.domain.rowkey;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * HBase RowKey
 *
 * @author: lijiawei04
 * @date: 2021/5/14 4:48 下午
 */
public interface RowKey {

    /**
     * 获取 RowKey 分隔符
     *
     * @return 分隔符
     */
    @JSONField(serialize = false)
    default String getSeparator() {
        return "_";
    }

    /**
     * 转换为 HBase RowKey
     *
     * @return HBase RowKey
     */
    @JSONField(serialize = false)
    String parse();

    /**
     * HBase RowKey 转为 java Obj
     *
     * @param rowKey HBase RowKey
     * @return       java Obj
     */
    @JSONField(serialize = false)
    RowKey from(String rowKey);

    /**
     * 获取 RowKey 中元素的数量
     *
     * @return RowKey 中元素的数量
     */
    @JSONField(serialize = false)
    default Integer getElementLength() {
        return getClass().getDeclaredFields().length;
    }

}
