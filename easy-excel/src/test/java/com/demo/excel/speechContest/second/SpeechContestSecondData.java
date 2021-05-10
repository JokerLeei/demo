package com.demo.excel.speechContest.second;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 复赛
 *
 * @author: lijiawei04
 * @date: 2021/4/23 5:21 下午
 */
@Data
public class SpeechContestSecondData {

    @ExcelProperty("序号")
    private Long id;

    @ExcelProperty("是否晋级")
    private String isPass;

}
