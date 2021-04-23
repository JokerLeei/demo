package com.demo.excel.speechContest.first;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 5:23 下午
 */
@Data
@AllArgsConstructor
public class SpeechContestUserIdData {

    @ExcelProperty("user_id")
    private Long userId;

}
