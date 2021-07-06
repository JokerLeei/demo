package com.demo.example.util.graphic;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * java画图工具类
 *
 * @author: lijiawei04
 * @date: 2020/10/15 8:05 下午
 */
@Slf4j
public abstract class GraphicsUtil {

    /**
     * 缓存已根据url加载的bufferedImage对象
     */
    private static final LoadingCache<String, BufferedImage> IMAGE_CACHE = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterAccess(24, TimeUnit.HOURS)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, BufferedImage>() {
                // 若缓存没命中则执行load方法并将结果加入缓存
                @Override
                public BufferedImage load(String key) throws Exception {
                    return ImageIO.read(new URL(key));
                }
            });

    /**
     * 根据url获取图片对象
     *
     * @return 图片对象
     */
    @SneakyThrows
    public static BufferedImage getBufferedImage(String imgUrl) {
        long startTime = System.currentTimeMillis();
        BufferedImage bufferedImage = IMAGE_CACHE.get(imgUrl);
        log.info("image cache info:[{}]", IMAGE_CACHE.stats());
        long endTime = System.currentTimeMillis();
        log.info("根据url加载图片 url is:[{}], 耗时:[{}]", imgUrl, endTime - startTime);
        return bufferedImage;
    }

    /**
     * 平滑拉伸图片
     */
    public static BufferedImage resizeImage(BufferedImage bfi, int x, int y) {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    /**
     * 图片裁剪，正方形裁剪成圆形
     */
    public static BufferedImage convertCircular(BufferedImage sourceImage) {
        // 透明底的图片
        BufferedImage circleImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        Graphics2D graphics = circleImage.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        graphics.setComposite(ac);
        graphics.setBackground(Color.WHITE);
        graphics.setClip(shape);
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.setBackground(Color.WHITE);
        graphics.dispose();
        return circleImage;
    }

}
