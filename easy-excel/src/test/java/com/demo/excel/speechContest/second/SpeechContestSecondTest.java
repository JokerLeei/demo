package com.demo.excel.speechContest.second;

import com.alibaba.excel.EasyExcel;

import org.junit.Test;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

/**
 * 复赛上传分数
 *
 * @author: lijiawei04
 * @date: 2021/4/23 5:22 下午
 */
@Slf4j
public class SpeechContestSecondTest {

    private static final String BASE_PATH = "/Users/bjhl/Desktop/excel";

    @Test
    public void uploadData() {
        String fileName = BASE_PATH + File.separator + "晋级结果.xlsx";

        EasyExcel.read(fileName, SpeechContestSecondData.class, new SpeechContestSecondDataListener())
                .sheet()
                .doRead();
    }

}
