package com.demo.excel.speechContest.second;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lijiawei04
 * @date: 2021/4/23 5:30 下午
 */
@Slf4j
public class SpeechContestSecondDataListener extends AnalysisEventListener<SpeechContestSecondData> {

    private static final Map<Long, String> map = new HashMap<>(2000);
    private static final AtomicInteger needUpdateCount = new AtomicInteger(0);

    @Override
    public void invoke(SpeechContestSecondData data, AnalysisContext context) {
        Long id = data.getId();
        String isPass = data.getIsPass();

        if (map.containsKey(id)) {
            log.warn("重复的id, id:[{}]", id);
            return;
        }
        if (id == -1) {
            return;
        }
        map.put(id, data.getIsPass());


        if ("是".equals(isPass)) {
            needUpdateCount.incrementAndGet();
            System.out.println("UPDATE gaotu.speech_contest_work SET is_pass = 1 WHERE id = " + id + ";");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.warn("总记录数(id去重) size is:[{}]", map.size());
        log.warn("晋级数量(id去重) size is:[{}]", needUpdateCount.get());
    }
}
