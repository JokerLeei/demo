package com.demo.excel.speechContest.first;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 2:34 下午
 */
@Data
public class SpeechContestData2 {

    @ExcelProperty("user_id")
    private Long userId;

}
