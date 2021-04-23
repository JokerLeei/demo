package com.demo.excel.write.data;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * excel 基础数据类
 *
 * @author: lijiawei04
 * @date: 2021/2/26 2:21 下午
 */
@Data
@AllArgsConstructor
public class IndexData {

    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;

    @ExcelProperty(value = "日期标题", index = 1)
    private Date date;

    /**
     * 这里设置3 会导致第二列空的
     */
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;
}
