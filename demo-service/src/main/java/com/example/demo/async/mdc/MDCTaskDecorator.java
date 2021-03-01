package com.example.demo.async.mdc;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author: lijiawei04
 * @date: 2021/2/3 6:39 下午
 */
public class MDCTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        // Right now: Web thread context !
        // (Grab the current thread MDC data)
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        if (!CollectionUtils.isEmpty(contextMap)) {
            return () -> {
                try {
                    // Right now: @Async thread context !
                    // (Restore the Web thread context's MDC data)
                    MDC.setContextMap(contextMap);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        }
        return runnable;
    }
}
