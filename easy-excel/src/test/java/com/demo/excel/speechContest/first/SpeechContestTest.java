package com.demo.excel.speechContest.first;

import com.alibaba.excel.EasyExcel;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 2:15 下午
 */
@Slf4j
public class SpeechContestTest {

    private static final String BASE_PATH = "/Users/bjhl/Desktop/excel";

    @Test
    public void test() {
        String fileName = BASE_PATH + File.separator + "星象画像_高途高中正价课-9.8W.xlsx";
        String resultName = BASE_PATH + File.separator + "result.xlsx";

        List<Long> allUserIdList = new ArrayList<>(100000);

        EasyExcel.read(fileName, SpeechContestData.class, new SpeechContestListener(allUserIdList))
                .sheet()
                .doRead();

        List<Long> excludeUserIdList = new ArrayList<>(25000);

        EasyExcel.read(fileName, SpeechContestData2.class, new SpeechContestListener2(excludeUserIdList))
                .sheet(1)
                .doRead();

        allUserIdList.retainAll(excludeUserIdList);


        List<SpeechContestUserIdData> list = new ArrayList<>(100000);
        for (Long userId : allUserIdList) {
            list.add(new SpeechContestUserIdData(userId));
        }

        log.info("交集:{}", allUserIdList);
        EasyExcel.write(resultName, SpeechContestUserIdData.class)
                .sheet("交集")
                .doWrite(list);
        
    }


}
