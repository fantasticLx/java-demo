/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zp.image.dto.ImageParamDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片工具类
 *
 * @author Victor Zhang
 * @date 2017/1/16.
 */
public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static void toFile(String oldFile, String newFile, ImageParamDTO params) throws IOException {
        if (StringUtils.isBlank(oldFile) || StringUtils.isBlank(newFile)) {
            logger.error("原文件名或目标文件名为空");
            return;
        }
        Thumbnails.Builder builder = getBuilder(oldFile, params);
        if (null == builder) {
            return;
        }
        builder.toFile(newFile);
    }

    public static BufferedImage toBufferedImage(String oldFile, ImageParamDTO params) throws IOException {
        if (StringUtils.isBlank(oldFile)) {
            logger.error("原文件名或目标文件名为空");
            return null;
        }
        Thumbnails.Builder builder = getBuilder(oldFile, params);
        if (null == builder) {
            return null;
        }
        return builder.asBufferedImage();
    }

    public static OutputStream toOutputStream(String oldFile, String newFile, ImageParamDTO params) throws IOException {
        if (StringUtils.isBlank(oldFile)) {
            logger.error("原文件名或目标文件名为空");
            return null;
        }
        Thumbnails.Builder builder = getBuilder(oldFile, params);
        if (null == builder) {
            return null;
        }

        OutputStream os = new FileOutputStream(newFile);
        builder.toOutputStream(os);
        return os;
    }

    private static Thumbnails.Builder getBuilder(String oldFile, ImageParamDTO params) throws IOException {
        if (null == params) {
            logger.error("目标图片参数为空");
            return null;
        }

        Thumbnails.Builder builder = Thumbnails.of(new File(oldFile));
        if (null != params.getScale()) {
            builder.scale(params.getScale());
        } else if (null != params.getWidth() && null != params.getHeight()) {
            builder.size(params.getWidth(), params.getHeight());
        } else {
            logger.error("目标图片参数错误");
        }

        if (null != params.getRotate()) {
            builder.rotate(params.getRotate());
        }

        if (null != params.getQuality()) {
            builder.outputQuality(params.getQuality());
        }

        if (StringUtils.isNotBlank(params.getWartermarkPath())) {
            builder.watermark(params.getWartermarkPosition(), ImageIO.read(new File(params.getWartermarkPath())), params.getWartermarkOpacity());
        }

        return builder;
    }

    /**
     * 获取文件的 ContentType
     *
     * @param content
     * @return
     * @throws MagicParseException
     * @throws MagicException
     * @throws MagicMatchNotFoundException
     */
    public static String getContentType(byte[] content) throws MagicParseException, MagicException, MagicMatchNotFoundException {
        MagicMatch match = Magic.getMagicMatch(content);
        return match.getMimeType();
    }
}
