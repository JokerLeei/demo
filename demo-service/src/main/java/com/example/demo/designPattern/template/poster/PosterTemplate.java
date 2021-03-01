package com.example.demo.designPattern.template.poster;

import com.example.demo.util.graphic.GraphicsUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 画海报 模板方法
 *
 * 画不同海报时直接继承此抽象类并实现下面两个方法<br>
 * 提供背景图: {@link this#backgroundUrl()}<br>
 * 提供海报元素: {@link this#element()}
 *              {@link AbstractElement}
 *              {@link TextElement}
 *              {@link ImageElement}<br>
 * 后置处理(存DB、alert等), 实现{@link this#afterDraw(String)}
 *
 * @author: lijiawei04
 * @date: 2021/1/20 9:31 下午
 */
@Slf4j
public abstract class PosterTemplate {

    /**
     * 模板方法-画海报
     *
     * @return 生成的海报url
     */
    public final String drawPoster() {
        if (CollectionUtils.isEmpty(element())) {
            return "";
        }
        List<AbstractElement> elementList = element().stream()
                .sorted(Comparator.comparing(AbstractElement::getOrder, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // 获取背景图
        BufferedImage backgroundImage = GraphicsUtil.getBufferedImage(backgroundUrl());

        // 初始化图像对象
        Graphics2D graphics2D = backgroundImage.createGraphics();

        // 按顺序画元素(order小的后画)
        if (!CollectionUtils.isEmpty(elementList)) {
            elementList.forEach(element -> {
                // 画文字
                if (element.getClass() == TextElement.class) {
                    graphics2D.setFont(((TextElement) element).getFont());
                    graphics2D.setColor(((TextElement) element).getColor());

                    graphics2D.drawString(((TextElement) element).getContent(),
                                          element.getPositionX(),
                                          element.getPositionY());
                }
                // 画图片
                if (element.getClass() == ImageElement.class) {
                    graphics2D.drawImage(((ImageElement) element).getImage(),
                                         element.getPositionX(),
                                         element.getPositionY(),
                                         null);
                }
            });
        }

        // 上传海报图片对象并获取url


        String posterUrl = "posterUrl";
        // 画海报后置处理
        afterDraw(posterUrl);
        return posterUrl;
    }

    /**
     * 设置背景图url
     *
     * @return 背景图url
     */
    protected abstract String backgroundUrl();

    /**
     * 设置海报中的元素
     *
     * @return 海报中的元素
     *         基元素 {@link AbstractElement}
     *         文字元素 {@link TextElement}
     *         图片元素 {@link ImageElement}
     */
    protected abstract List<AbstractElement> element();

    /**
     * 画海报后置处理(钩子方法)
     *
     * @param posterUrl 生成的海报url
     */
    protected void afterDraw(String posterUrl) {
    }

    /**
     * 获取默认字体
     */
    @SneakyThrows
    protected Font getDefaultFont() {
        String fontFilePath = "";
        // 本地调试用⬇
//        fontFilePath = "/Users/bjhl/IdeaProjects/coin-mall-server/font/FZLTHJW.TTF";
        log.info("加载默认字体文件... 字体文件所在目录:[{}]", fontFilePath);
        File fontFile = new File(fontFilePath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        font = font.deriveFont(Font.PLAIN);
        return font;
    }

//    /**
//     * 初始化图像对象
//     *
//     * @param graphics      初始化前的图像对象
//     * @return              初始化后的图像对象
//     */
//    private Graphics2D initGraphics(Graphics2D graphics) {
//        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
//        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
//        graphics.setComposite(ac);
//        graphics.setBackground(Color.WHITE);
//        return graphics;
//    }

}

@Slf4j
@Component
class A extends PosterTemplate {

    @Override
    public String backgroundUrl() {
        return "https://i.gsxcdn.com/955215372_dggh2ylh.png";
    }

    @Override
    protected List<AbstractElement> element() {
        return new ArrayList<AbstractElement>() {{
            add(ImageElement.build(GraphicsUtil.getBufferedImage("https://i.gsxcdn.com/955215372_dggh2ylh.png")).setPosition(50, 50).setOrder(5));
            add(ImageElement.build(GraphicsUtil.getBufferedImage("https://i.gsxcdn.com/955215372_dggh2ylh.png")).setPosition(100, 100).setOrder(4));
        }};
    }

    @Override
    protected void afterDraw(String posterUrl) {
        log.info(posterUrl);
    }

    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.drawPoster());
        System.out.println(a.drawPoster());
    }


}