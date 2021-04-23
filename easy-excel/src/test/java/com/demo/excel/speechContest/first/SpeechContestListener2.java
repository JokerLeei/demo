package com.demo.excel.speechContest.first;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 2:39 下午
 */
@Slf4j
public class SpeechContestListener2 extends AnalysisEventListener<SpeechContestData2> {

    private final List<Long> excludeUserIdList;

    public SpeechContestListener2(List<Long> list) {
        excludeUserIdList = list;
    }

    @Override
    public void invoke(SpeechContestData2 data, AnalysisContext context) {
        excludeUserIdList.add(data.getUserId());
//        log.info("{}", data.getUserId());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！excludeUserId size is:[{}]", excludeUserIdList.size());
    }

}
