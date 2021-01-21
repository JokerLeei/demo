package com.example.demo.designPattern.template.poster;

import java.awt.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 海报 文字元素
 *
 * @author: lijiawei04
 * @date: 2021/1/21 10:57 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextElement extends AbstractElement {

    /**
     * 文本内容
     */
    private String content;

    /**
     * 颜色
     */
    private Color color;

    /**
     * 字体
     */
    private Font font;

    public static AbstractElement build(String content, Color color, Font font) {
        TextElement textElement = new TextElement();
        textElement.setContent(content);
        textElement.setColor(color);
        textElement.setFont(font);
        return textElement;
    }

}
