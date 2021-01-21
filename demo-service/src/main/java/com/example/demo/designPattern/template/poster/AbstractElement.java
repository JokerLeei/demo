package com.example.demo.designPattern.template.poster;

import lombok.Data;

/**
 * 海报上的元素 基类
 *
 * @author: lijiawei04
 * @date: 2021/1/21 10:04 上午
 */
@Data
public abstract class AbstractElement {

    /**
     * 距离左上角水平偏移量(px)
     */
    private Integer positionX = 0;

    /**
     * 距离左上角垂直偏移量(px)
     */
    private Integer positionY = 0;

    /**
     * 元素顺序(越小越靠上)
     */
    private Integer order = Integer.MAX_VALUE;

    public AbstractElement setPosition(Integer positionX, Integer positionY) {
        this.setPositionX(positionX);
        this.setPositionY(positionY);
        return this;
    }

    public AbstractElement setOrder(Integer order) {
        this.order = order;
        return this;
    }

}
