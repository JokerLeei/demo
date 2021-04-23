package com.demo.excel.read.data;

import java.util.Date;

import lombok.Data;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author: lijiawei04
 * @date: 2021/2/26 7:06 下午
 */
@Data
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
