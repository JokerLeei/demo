package com.example.demo.designPattern.template.poster;

import java.awt.image.BufferedImage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 海报图片元素
 *
 * @author: lijiawei04
 * @date: 2021/1/21 10:58 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageElement extends AbstractElement {

    /**
     * 图片对象
     */
    private BufferedImage image;

    public static AbstractElement build(BufferedImage image) {
        ImageElement imageElement = new ImageElement();
        imageElement.setImage(image);
        return imageElement;
    }

}
