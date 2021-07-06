package com.demo.example.util.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author: lijiawei04
 * @date: 2021/3/10 9:29 下午
 */
public interface WxMessage {

    String MESSAGE_TYPE = "msgtype";

    /**
     * 获取微信消息类型
     * <ul>
     *     <li>text</li>
     *     <li>markdown</li>
     *     <li>image</li>
     *     <li>news</li>
     * </ul>
     *
     * @return {@link WxMessageTypeEnum}
     */
    @JSONField(serialize = false)
    WxMessageTypeEnum getMessageType();

    @JSONField(serialize = false)
    default String getMessageBody() {
        Map<Object, Object> messageBody = ImmutableMap.builder()
                .put(MESSAGE_TYPE, getMessageType().getType())
                .put(getMessageType().getType(), JSON.toJSON(this)).build();
        return JSONObject.toJSONString(messageBody);
    }

}
