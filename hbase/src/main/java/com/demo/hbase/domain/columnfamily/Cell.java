package com.demo.hbase.domain.columnfamily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * HBase Cell
 *
 * @author: lijiawei04
 * @date: 2021/5/24 7:40 下午
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Cell {

    private String qualifier;

    private String value;

}
