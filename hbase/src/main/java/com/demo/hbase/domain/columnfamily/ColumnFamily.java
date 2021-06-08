package com.demo.hbase.domain.columnfamily;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * HBase ColumnFamily
 *
 * @author: lijiawei04
 * @date: 2021/5/14 5:00 下午
 */
public interface ColumnFamily {

    /**
     * 获取名称
     *
     * @return HBase ColumnFamily
     */
    @JSONField(serialize = false)
    String getName();

    /**
     * 获取列
     *
     * @return HBase Cell 单元格
     */
    @JSONField(serialize = false)
    default List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();

        JSONObject object = (JSONObject) JSONObject.toJSON(this);
        if (object != null) {
            object.forEach((key, value) -> cells.add(new Cell(key, String.valueOf(value))));
        }
        return cells;
    }

}
