package com.example.demo.util.wx;

import lombok.Data;

/**
 * markdown类型消息
 *
 * @author: lijiawei04
 * @date: 2021/3/10 10:20 下午
 */
@Data
public class MarkdownWxMessage implements WxMessage {

    /**
     * markdown内容，最长不超过4096个字节，必须是utf8编码
     */
    private String content;

    @Override
    public WxMessageTypeEnum getMessageType() {
        return WxMessageTypeEnum.MARKDOWN;
    }

    public static void main(String[] args) {
        MarkdownWxMessage message = new MarkdownWxMessage();
        message.setContent("啊啊啊");
        System.out.println(message.getMessageBody());
    }

}
