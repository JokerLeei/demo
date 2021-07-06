package com.demo.example.designPattern.observer.eventListener;

import org.springframework.context.ApplicationEvent;

/**
 * @author: lijiawei04
 * @date: 2020-09-07 14:50
 * @description: 定义事件
 */
public class AccessEvent extends ApplicationEvent {

    private Request request;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AccessEvent(Object source, Request request) {
        // source: 事件发送源, 事件发送者
        super(source);
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }

}
