package com.demo.excel.write.converter;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

import lombok.Data;

/**
 * excel 基础数据类
 *
 * @author: lijiawei04
 * @date: 2021/2/26 2:56 下午
 */
@Data
public class LongestMatchColumnWidthData {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题很长日期标题很长日期标题很长很长")
    private Date date;

    @ExcelProperty("数字")
    private Double doubleData;

}
