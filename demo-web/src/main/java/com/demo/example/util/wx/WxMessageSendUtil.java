package com.demo.example.util.wx;

import com.mashape.unirest.http.Unirest;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送企业微信消息工具类
 *
 * @author: lijiawei04
 * @date: 2021/3/10 11:15 下午
 */
@Slf4j
public abstract class WxMessageSendUtil {

    public static void send(String requestUrl, WxMessage message) {
        try {
            log.info("企业微信发送消息... messageBody:[{}]", message.getMessageBody());
            Unirest.post(requestUrl)
                    .body(message.getMessageBody())
                    .asStringAsync();
        } catch (Throwable ex) {
            log.info("企业微信消息发送失败!", ex);
        }
    }

    public static void main(String[] args) {
        String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=1b5d01d9-9d10-4010-ab78-68b793136a98";
        String content = "实时新增用户反馈<font color=\"warning\">132例</font>，请相关同事注意。\n"
                + "> 类型:<font color=\"comment\">用户反馈</font>\n"
                + "> 普通用户反馈:<font color=\"comment\">117例</font>\n"
                + "> VIP用户反馈:<font color=\"comment\">15例</font>";

        MarkdownWxMessage message = new MarkdownWxMessage();
        message.setContent(content);

        WxMessageSendUtil.send(requestUrl, message);
    }

}
