package com.demo.excel.speechContest.first;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/2 2:24 下午
 */
@Slf4j
public class SpeechContestListener extends AnalysisEventListener<SpeechContestData> {

    public SpeechContestListener(List<Long> list) {
        userIdList = list;
    }

    private final List<Long> userIdList;

    @Override
    public void invoke(SpeechContestData data, AnalysisContext context) {
//        log.info("{}", data.getUserId());
        userIdList.add(data.getUserId());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！userId size is:[{}]", userIdList.size());
    }
}
