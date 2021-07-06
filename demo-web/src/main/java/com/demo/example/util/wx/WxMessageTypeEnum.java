package com.demo.example.util.wx;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信消息类型枚举
 *
 * @author: lijiawei04
 * @date: 2021/3/10 10:46 下午
 */
@Getter
@AllArgsConstructor
public enum WxMessageTypeEnum {

    /**
     * 文本
     */
    TEXT("text"),

    /**
     * markdown类型
     */
    MARKDOWN("markdown"),

    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 图文类型
     */
    NEWS("news");

    /**
     * 微信消息类型，String类型
     */
    private final String type;

    public static WxMessageTypeEnum of(String type) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.getType(), type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("type is invalid!"));
    }

}
