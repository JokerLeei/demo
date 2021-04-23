package com.example.demo.excel.speechContest.first;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 2:21 下午
 */
@Data
public class SpeechContestData {

    @ExcelProperty("序号")
    private Integer index;

    @ExcelProperty("用户number")
    private Long userId;

}
