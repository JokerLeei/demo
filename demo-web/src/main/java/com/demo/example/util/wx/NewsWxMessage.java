package com.demo.example.util.wx;

import java.util.List;

import lombok.Data;

/**
 * 图文类型消息
 *
 * @author: lijiawei04
 * @date: 2021/3/10 10:30 下午
 */
@Data
public class NewsWxMessage implements WxMessage {

    /**
     * 图文消息，一个图文消息支持1到8条图文
     */
    private List<Articles> articles;

    @Override
    public WxMessageTypeEnum getMessageType() {
        return WxMessageTypeEnum.NEWS;
    }

    @Data
    public static class Articles {

        /**
         * 标题，不超过128个字节，超过会自动截断
         */
        private String title;

        /**
         * 描述，不超过512个字节，超过会自动截断
         */
        private String description;

        /**
         * 点击后跳转的链接。
         */
        private String url;

        /**
         * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图 1068*455，小图150*150。
         */
        private String picurl;

    }
}
