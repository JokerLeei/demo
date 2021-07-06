package com.demo.example.util.wx;

import lombok.Data;

/**
 * 图片类型消息
 *
 * @author: lijiawei04
 * @date: 2021/3/10 10:21 下午
 */
@Data
public class ImageWxMessage implements WxMessage {

    /**
     * 图片内容的base64编码
     */
    private String base64;

    /**
     * 图片内容（base64编码前）的md5值
     */
    private String md5;

    @Override
    public WxMessageTypeEnum getMessageType() {
        return WxMessageTypeEnum.IMAGE;
    }
}
