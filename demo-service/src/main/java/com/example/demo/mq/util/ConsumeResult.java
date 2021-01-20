package com.example.demo.mq.util;

import com.aliyun.openservices.ons.api.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * ONS 消息消费结果
 *
 * @author: lijiawei04
 * @date: 2021/1/20 10:52 上午
 */
@Data
@AllArgsConstructor
public class ConsumeResult<T> {

    /**
     * 消费结果
     */
    private ConsumeResultStatus status;

    /**
     * 从{@link Message}中提取到需要进行后置处理的数据
     */
    private T data;

    public static <T> ConsumeResult<T> success() {
        return new ConsumeResult<>(ConsumeResultStatus.SUCCESS, null);
    }

    public static <T> ConsumeResult<T> success(T data) {
        return new ConsumeResult<>(ConsumeResultStatus.SUCCESS, data);
    }

    public static <T> ConsumeResult<T> fail() {
        return new ConsumeResult<>(ConsumeResultStatus.FAIL, null);
    }

    public static <T> ConsumeResult<T> failNotRetry() {
        return new ConsumeResult<>(ConsumeResultStatus.FAIL_NOT_RETRY, null);
    }

    @Getter
    @AllArgsConstructor
    public enum ConsumeResultStatus {
        // 消费成功
        SUCCESS,

        // 消费失败(该条消息需要被重新消费)
        FAIL,

        // 消费失败(该条消息不需要被重新消费)
        FAIL_NOT_RETRY
    }

}
