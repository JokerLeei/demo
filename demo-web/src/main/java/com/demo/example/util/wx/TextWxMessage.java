package com.demo.example.util.wx;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

import lombok.Data;

/**
 * 文本类型消息
 *
 * @author: lijiawei04
 * @date: 2021/3/10 10:16 下午
 */
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class TextWxMessage implements WxMessage {

    /**
     * 文本内容，最长不超过2048个字节，必须是utf8编码
     */
    private String content;

    /**
     * userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人，如果开发者获取不到userid，可以使用mentioned_mobile_list
     */
    private List<String> mentionedList;

    /**
     * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
     */
    private List<String> mentionedMobileList;

    @Override
    public WxMessageTypeEnum getMessageType() {
        return WxMessageTypeEnum.TEXT;
    }
}